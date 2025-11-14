package mis.services;

import mis.models.*;

public class ReportManager
{
    private DataManager dataManager;

    public ReportManager(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    public String buildGradesReport()
    {
        Reportable report = new GradesReport(dataManager);
        return report.generateReport();
    }

    public String buildAttendanceReport()
    {
        Reportable report = new AttendanceReport(dataManager);
        return report.generateReport();
    }
}