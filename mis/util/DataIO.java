package mis.util;

import mis.services.DataManager;
import mis.models.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Utility class for saving and loading MIS system data to and from text files.
 *
 * <p>{@code DataIO} provides static methods to persist the state of a
 * {@link mis.services.DataManager} and reconstruct it later. Data is stored
 * in a simple line‑based format where each line begins with a record type
 * identifier ({@code "STUDENT"}, {@code "STAFF"}, {@code "COURSE"}) followed
 * by pipe‑delimited fields.</p>
 *
 * <p><b>Key features:</b></p>
 * <ul>
 *   <li>Students, staff and courses are each serialised into a single line.</li>
 *   <li>Grades, staff tasks and enrolled student IDs are stored as
 *       comma‑separated values.</li>
 *   <li>Attendance is stored as a double value.</li>
 *   <li>Uses {@link PrintWriter}/{@link FileWriter} for saving and
 *       {@link BufferedReader}/{@link FileReader} for loading.</li>
 *   <li>Exceptions are caught and reported to the console without halting execution.</li>
 * </ul>
 *
 * <p><b>Loading strategy:</b></p>
 * <ul>
 *   <li>Data is read into memory and processed in two passes to ensure
 *       referential integrity.</li>
 *   <li><b>Pass 1:</b> Load all courses so they exist before students reference them.</li>
 *   <li><b>Pass 2:</b> Load students and staff, linking students to courses
 *       and restoring grades, attendance and staff tasks.</li>
 * </ul>
 *
 * <p><b>Format examples:</b></p>
 * <ul>
 *   <li>Student: {@code STUDENT|id|name|email|courseCode|gradesCSV|attendancePercentage}</li>
 *   <li>Staff: {@code STAFF|id|name|email|role|department|tasksCSV}</li>
 *   <li>Course: {@code COURSE|code|title|enrolledIdsCSV}</li>
 * </ul>
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
     *   <li>Student: {@code STUDENT|id|name|email|courseCode|gradesCSV|attendancePercentage}</li>
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
                writer.println("STAFF|" + staff.getId() + "|" + staff.getName() + "|" + staff.getEmail() + "|" + staff.getRole() + "|" + staff.getDepartment() + "|" + staff.getTasksCSV());
            }

            // Save courses
            for(Course course : manager.getCourses())
            {
                // Enrolled student IDs are stored as a comma-separated string
                String enrolled = course.getEnrolledIdsCSV();
                writer.println("COURSE|" + course.getCode() + "|" + course.getTitle() + "|" + enrolled);
            }

            System.out.println("\nData saved successfully to " + path);
        }
        catch(IOException exception)
        {
            System.out.println("\nError saving data: " + exception.getMessage());
        }
    }

    /**
     * Loads system data from a text file into the given {@link DataManager}.
     *
     * <p>This method uses a two-pass strategy to ensure referential integrity:</p>
     * <ul>
     *   <li><b>Pass 1:</b> Load all {@code COURSE} records first so that courses exist
     *       before students reference them.</li>
     *   <li><b>Pass 2:</b> Load {@code STUDENT} and {@code STAFF} records, linking
     *       students to courses and restoring grades, attendance, and staff tasks.</li>
     * </ul>
     *
     * <p>File format expectations:</p>
     * <ul>
     *   <li>Student: {@code STUDENT|id|name|email|courseCode|gradesCSV|attendancePercentage}</li>
     *   <li>Staff: {@code STAFF|id|name|email|role|department|tasksCSV}</li>
     *   <li>Course: {@code COURSE|code|title|enrolledIdsCSV}</li>
     * </ul>
     *
     * <p><b>Design Notes:</b></p>
     * <ul>
     *   <li>Grades and enrolled IDs are comma-separated.</li>
     *   <li>Attendance is stored as a double value.</li>
     *   <li>Tasks are stored as comma-separated strings.</li>
     *   <li>Errors are caught and reported to the console without halting execution.</li>
     * </ul>
     *
     * @param manager the {@link DataManager} to populate with loaded data
     * @param path    the file path to load from
     */
    public static void loadFromFile(DataManager manager, String path)
    {
        try
        {
            // Read all lines in memory first so they can be processed in 2 passes
            ArrayList<String> lines = new ArrayList<>();

            try(BufferedReader reader = new BufferedReader(new FileReader(path)))
            {
                String line;
                while((line = reader.readLine()) != null)
                {
                    lines.add(line);
                }
            }

            // ---------- PASS 1: Load COURSES ----------
            // Courses must be loaded first so that students can be linked to them later
            for(String line : lines)
            {
                String[] parts = line.split("\\|");

                if(parts[0].equals("COURSE"))
                {
                    String code = parts[1];
                    String title = parts[2];

                    Course course = new Course(code, title);
                    manager.addCourse(course);

                    // Restore enrolled student IDs if present
                    if(parts.length > 3 && !parts[3].isEmpty())
                    {
                        for(String id : parts[3].split(","))
                        {
                            course.enrolStudent(Integer.parseInt(id));
                        }
                    }
                }
            }

            // ---------- PASS 2: Load STUDENTS and STAFF ----------
            // Now that courses exist, students can be linked correctly
            for(String line : lines)
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
                        double attendance = Double.parseDouble(parts[6]);

                        Student student = new Student(id, name, email);
                        student.setAttendancePercentage(attendance);

                        // Link student to course if code is provided and course exists
                        if(!courseCode.isEmpty())
                        {
                            Course course = manager.findCourseByCode(courseCode);
                            if(course != null)
                            {
                                student.setCourse(course);
                            }
                        }

                        // Restore grades if present
                        if(!parts[5].isEmpty())
                        {
                            for(String grade : parts[5].split(","))
                            {
                                student.addGrade(Integer.parseInt(grade));
                            }
                        }
                        manager.addStudent(student);
                    }

                    case "STAFF" ->
                    {
                        int id = Integer.parseInt(parts[1]);
                        String name = parts[2];
                        String email = parts[3];
                        String role = parts[4];
                        String department = parts[5];

                        Staff staff = new Staff(id, name, email, role, department);

                        // Restore tasks if present
                        if(parts.length > 6 && !parts[6].isEmpty())
                        {
                            for(String task : parts[6].split(","))
                            {
                                staff.getTasks().add(task);
                            }
                        }
                        manager.addStaff(staff);
                    } // case
                } // switch
            } // for

            System.out.println("\nData loaded successfully from " + path);
        } // try
        catch(IOException exception)
        {
            System.out.println("\nError loading data: " + exception.getMessage());
        } // catch
    } // loadFromFile
} // class