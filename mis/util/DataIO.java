package mis.util;

import mis.services.DataManager;
import mis.models.*;
import java.io.*;

public class DataIO
{
    public static void saveToFile(DataManager manager, String path)
    {
        try(PrintWriter writer = new PrintWriter(new FileWriter(path)))
        {
            // Save students
            for(Student s : manager.getStudents())
            {
                String courseCode;
                if (s.getCourse() != null)
                {
                    courseCode = s.getCourse().getCode();
                }
                else
                {
                    courseCode = "";
                }
                String grades = s.getGradesCSV();
                writer.println("STUDENT|" + s.getId() + "|" + s.getName() + "|" + s.getEmail() + "|" + courseCode + "|" + grades);
            }

            // Save staff
            for(Staff st : manager.getStaffMembers())
            {
                writer.println("STAFF|" + st.getId() + "|" + st.getName() + "|" + st.getEmail() + "|" + st.getRole() + "|" + st.getDepartment());
            }

            // Save courses
            for(Course c : manager.getCourses())
            {
                String enrolled = c.getEnrolledIdsCSV();
                writer.println("COURSE|" + c.getCode() + "|" + c.getTitle() + "|" + enrolled);
            }

            System.out.println("\nData saved successfully to " + path + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public static void loadFromFile(DataManager manager, String path)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(path)))
        {
            String line;
            while((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|");
                switch(parts[0])
                {
                    case "STUDENT" ->
                    {
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        String email = parts[3];
                        String courseCode = parts[4];
                        Student s = new Student(id, name, email);
                        if(!courseCode.isEmpty())
                        {
                            Course c = manager.findCourseByCode(courseCode);
                            if (c != null)
                            {
                                s.setCourse(c);
                            }
                        }
                        if(parts.length > 5 && !parts[5].isEmpty())
                        {
                            for(String g : parts[5].split(","))
                            {
                                s.addGrade(Integer.parseInt(g));
                            }
                        }
                        manager.addStudent(s);
                    }
                    case "STAFF" ->
                    {
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        String email = parts[3];
                        String role = parts[4];
                        String department = parts[5];
                        Staff st = new Staff(id, name, email, role, department);
                        manager.addStaff(st);
                    }
                    case "COURSE" ->
                    {
                        String code = parts[1];
                        String title = parts[2];
                        Course c = new Course(code, title);
                        manager.addCourse(c);
                        if(parts.length > 3 && !parts[3].isEmpty())
                        {
                            for(String idStr : parts[3].split(","))
                            {
                                c.enrolStudent(Integer.parseInt(idStr));
                            }
                        }
                    }
                }
            }
            System.out.println("\nData loaded successfully from " + path + "\n");
        }
        catch(IOException e)
        {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}