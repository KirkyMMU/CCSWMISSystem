package mis.models;

import mis.services.DataManager;

public class AttendanceReport implements Reportable
{
    private DataManager manager;

    public AttendanceReport(DataManager manager)
    {
        this.manager = manager;
    }

    @Override
    public String generateReport()
    {
        // Stub implementation
        return "\n--- Attendance Report ---\n(Not yet implemented)\n";
    }
}