package mis.models;

import mis.services.DataManager;

public class GradesReport implements Reportable
{
    private DataManager manager;

    public GradesReport(DataManager manager)
    {
        this.manager = manager;
    }

    @Override
    public String generateReport()
    {
        StringBuilder builder = new StringBuilder("\n--- Grades Report ---\n");

        for(Student s : manager.getStudents())
        {
            double avg = s.calculateAverage();
            String courseInfo;
            if(s.getCourse() != null)
            {
                courseInfo = s.getCourse().getCode();
            }
            else
            {
                courseInfo = "No course";
            }

            builder.append(s.getName())
                   .append(" (ID: ").append(s.getId()).append(")")
                   .append(" - Course: ").append(courseInfo)
                   .append(" | Average Grade: ");
            
            if(avg == 0)
            {
                builder.append("N/A");
            }
            else
            {
                builder.append(avg);
            }

            builder.append("\n");
        }
        return builder.toString();
    }
}