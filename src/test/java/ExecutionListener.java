import org.influxdb.dto.Point;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;

public class ExecutionListener implements ITestListener {

    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSuccess(ITestResult iTestResult) {
        this.sendTestMethodStatus(iTestResult, "PASS");
    }

    public void onTestFailure(ITestResult iTestResult) {
        this.sendTestMethodStatus(iTestResult, "FAIL");
    }

    public void onTestSkipped(ITestResult iTestResult) {
        this.sendTestMethodStatus(iTestResult, "SKIPPED");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }


    private void sendTestMethodStatus(ITestResult iTestResult, String status) {
        Point point = Point.measurement("testmethod")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("testclass", iTestResult.getTestClass().getName())
                .tag("name", iTestResult.getName())
                .tag("description", iTestResult.getMethod().getDescription())
                .tag("result", status)
                .addField("duration", (iTestResult.getEndMillis() - iTestResult.getStartMillis()))
                .addField("result",status)
                .build();
        ResultSender.send(point);
    }


}

