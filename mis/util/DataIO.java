package mis.util;

import mis.services.DataManager;
import mis.models.*;
import java.io.*;

/**
 * Utility class for saving and loading MIS system data to and from text files.
 * 
 * <p>Provides static methods to persist the state of {@link DataManager} and
 * reconstruct it later. Data is stored in a simple line-based format where
 * each line begins with a record type identifier ("STUDENT", "STAFF", "COURSE")
 * followed by pipe-delimited fields.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses {@link PrintWriter} and {@link FileWriter} for saving data.</li>
 *   <li>Uses {@link BufferedReader} and {@link FileReader} for loading data.</li>
 *   <li>Each entity type (Student, Staff, Course) is serialised into a single line.</li>
 *   <li>Grades and enrolled student IDs are stored as comma-separated values.</li>
 *   <li>Exceptions are caught and reported to the console without halting execution.</li>
 * </ul>
 * </p>
 */

public class DataIO
{
    /**
     * Saves the current state of the system to a text file.
     * 
     * <p>Each student, staff member and course is written as a line of text
     * with fields separated by the pipe character ("|").</p>
     * 
     * <p>Format examples:
     * <ul>
     *   <li>Student: {@code STUDENT|id|name|email|courseCode|gradesCSV}</li>
     *   <li>Staff: {@code STAFF|id|name|email|role|department}</li>
     *   <li>Course: {@code COURSE|code|title|enrolledIdsCSV}</li>
     * </ul>
     * </p>
     * 
     * @param manager the {@link DataManager} containing system data
     * @param path    the file path to save to
     */
    public static void saveToFile(DataManager manager, String path)
    {
        try(PrintWriter writer = new PrintWriter(new FileWriter(path)))
        {
            // Save students
            for(Student student : manager.getStudents())
            {
                // If student has a course, include the code otherwise leave blank
                String courseCode;
                if (student.getCourse() != null)
                {
                    courseCode = student.getCourse().getCode();
                }
                else
                {
                    courseCode = "";
                }

                // Grades are stored as a comma-separated string
                String grades = student.getGradesCSV();

                writer.println("STUDENT|" + student.getId() + "|" + student.getName() + "|" + student.getEmail() + "|" + courseCode + "|" + grades + "|" + student.getAttendancePercentage());
            }

            // Save staff
            for(Staff staff : manager.getStaffMembers())
            {
                writer.println("STAFF|" + staff.getId() + "|" + staff.getName() + "|" + staff.getEmail() + "|" + staff.getRole() + "|" + staff.getDepartment());
            }

            // Save courses
            for(Course course : manager.getCourses())
            {
                // Enrolled student IDs are stored as a comma-separated string
                String enrolled = course.getEnrolledIdsCSV();
                writer.println("COURSE|" + course.getCode() + "|" + course.getTitle() + "|" + enrolled);
            }

            System.out.println("\nData saved successfully to " + path + "\n");
        }
        catch(IOException error)
        {
            System.out.println("Error saving data: " + error.getMessage());
        }
    }

    /**
     * Loads system data from a text file into the given DataManager.
     * 
     * <p>Reads each line, splits it into fields and reconstructs the appropriate
     * entity (Student, Staff, Course). Students may be linked to existing courses
     * and grades/enrolled IDs are parsed from comma-separated values.</p>
     * 
     * @param manager the {@link DataManager} to populate with loaded data
     * @param path    the file path to load to
     */
    public static void loadFromFile(DataManager manager, String path)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(path)))
        {
            String line;

            // Read file line by line
            while((line = reader.readLine()) != null)
            {
                String[] parts = line.split("\\|"); // Split fields by pipe delimiter

                switch(parts[0])
                {
                    case "STUDENT" ->
                    {
                        // Parse student fields
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        String email = parts[3];
                        String courseCode = parts[4];
                        double attendance = Double.parseDouble(parts[6]);

                        Student student = new Student(id, name, email);
                        student.setAttendancePercentage(attendance);

                        // Link student to existing course if code is provided
                        if(!courseCode.isEmpty())
                        {
                            Course c = manager.findCourseByCode(courseCode);
                            if (c != null)
                            {
                                student.setCourse(c);
                            }
                        }

                        // Parse grades if present
                        if(parts.length > 5 && !parts[5].isEmpty())
                        {
                            for(String g : parts[5].split(","))
                            {
                                student.addGrade(Integer.parseInt(g));
                            }
                        }

                        manager.addStudent(student);
                    }
                    case "STAFF" ->
                    {
                        // Parse staff fields
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        String email = parts[3];
                        String role = parts[4];
                        String department = parts[5];

                        Staff staff = new Staff(id, name, email, role, department);
                        manager.addStaff(staff);
                    }
                    case "COURSE" ->
                    {
                        // Parse course fields
                        String code = parts[1];
                        String title = parts[2];

                        Course course = new Course(code, title);
                        manager.addCourse(course);

                        // Parse enrolled student IDs if present 
                        if(parts.length > 3 && !parts[3].isEmpty())
                        {
                            for(String idStr : parts[3].split(","))
                            {
                                course.enrolStudent(Integer.parseInt(idStr));
                            } // for
                        } // if
                    } // case "COURSE"
                } // switch
            } // while
            System.out.println("\nData loaded successfully from " + path + "\n");
        } // try
        catch(IOException error)
        {
            System.out.println("Error loading data: " + error.getMessage());
        }
    }
}