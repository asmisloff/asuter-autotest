package ru.vniizht.asuter.autotest;

import io.qase.api.StepStorage;
import io.qase.api.config.QaseConfig;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreateCase;
import io.qase.client.model.ResultCreateStepsInner;
import io.qase.junit5.QaseExtension;
import io.qase.junit5.guice.module.Junit5Module;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

import static io.qase.api.utils.IntegrationUtils.getCaseId;
import static io.qase.api.utils.IntegrationUtils.getCaseTitle;
import static org.junit.platform.engine.TestExecutionResult.Status.SUCCESSFUL;

// Да, это костыль, но официальная библиотека для интеграции с Qase не позволяет изменять отправляемые
// в Qase данные (stacktrace, комментарии). В свою очередь Junit не позволяет кастомизировать порядок
// работы TestExecutionListener-ов, чтобы перехватить сообщение. По сути - это копипащенный QaseExtension
// с изменениями того, что должно попасть в отчет. Переопределить только необходимые методы тоже никак - всё приватное :(
@Slf4j
public class CustomTestExecutionListener extends QaseExtension {

    private static final String REPORTER_NAME = "JUnit 5";

    private final Set<TestIdentifier> startedTestIdentifiers =
            new ConcurrentSkipListSet<>(Comparator.comparing(TestIdentifier::hashCode));

    @Getter(lazy = true, value = AccessLevel.PRIVATE)
    private final QaseTestCaseListener qaseTestCaseListener = createQaseListener();

    static {
        System.setProperty(QaseConfig.QASE_CLIENT_REPORTER_NAME_KEY, REPORTER_NAME);
    }

    @Override
    public void executionStarted(TestIdentifier testIdentifier) {
        if (!testIdentifier.isTest()) {
            return;
        }
        getQaseTestCaseListener().onTestCaseStarted();
        startedTestIdentifiers.add(testIdentifier);
    }

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (!testIdentifier.isTest() || !startedTestIdentifiers.contains(testIdentifier)) {
            return;
        }
        TestSource testSource = testIdentifier.getSource().orElse(null);
        Method testMethod = testSource instanceof MethodSource
                ? getMethod((MethodSource) testSource)
                : null;

        getQaseTestCaseListener()
                .onTestCaseFinished(resultCreate -> setupResultItem(resultCreate, testExecutionResult, testMethod));
    }

    @Override
    public void testPlanExecutionFinished(TestPlan testPlan) {
        getQaseTestCaseListener().onTestCasesSetFinished();
    }

    private void setupResultItem(
            ResultCreate resultCreate, TestExecutionResult testExecutionResult, Method testMethod
    ) {
        Long caseId = getCaseId(testMethod);
        String caseTitle = null;
        if (caseId == null) {
            caseTitle = getCaseTitle(testMethod);
        }
        ResultCreate.StatusEnum status =
                testExecutionResult.getStatus() == SUCCESSFUL ? ResultCreate.StatusEnum.PASSED : ResultCreate.StatusEnum.FAILED;
        String comment = testExecutionResult.getThrowable()
                .flatMap(throwable -> Optional.of(throwable.toString())).orElse(null);
        if (comment != null) {
            comment = comment.replaceAll("Screenshot:.*|Page source:.*|Timeout:.*", "");
        }
        LinkedList<ResultCreateStepsInner> steps = StepStorage.stopSteps();
        resultCreate
                ._case(caseTitle == null ? null : new ResultCreateCase().title(caseTitle))
                .caseId(caseId)
                .status(status)
                .comment(comment)
                .steps(steps.isEmpty() ? null : steps)
                .defect(false);
    }


    private Method getMethod(MethodSource testSource) {
        try {
            Class<?> testClass = Class.forName(testSource.getClassName());
            return Arrays.stream(testClass.getDeclaredMethods())
                    .filter(method -> MethodSource.from(method).equals(testSource))
                    .findFirst().orElse(null);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private static QaseTestCaseListener createQaseListener() {
        return Junit5Module.getInjector().getInstance(QaseTestCaseListener.class);
    }
}
