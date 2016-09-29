package com.company;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by spawar5 on 9/26/2016.
 */
public class StudentInfoAccessTest {

    @Test
    public void testClassesTaught() {
        StudentInfoAccess access = new StudentInfoAccess();
        List<Student> list = loadCSV();
        Map map = new HashMap<String, Set<String>>();
        //Set<String> set = new Set<String>();
        map.put("Chemistry", new HashSet<String>(Arrays.asList("Joseph", "Jane")));
        map.put("History", new HashSet<String>(Arrays.asList("Smith", "Jane")));
        map.put("Mathematics", new HashSet<String>(Arrays.asList("Einstein", "Doe")));
        map.put("Physics", new HashSet<String>(Arrays.asList("Smith", "Einstein")));
        assertEquals(map, access.classesTaught(list));
    }

    @Test
    public void testClassesTaken() {
        StudentInfoAccess access = new StudentInfoAccess();
        List<Student> list = loadCSV();
        Map map = new HashMap<Integer, Set<String>>();
        map.put(1234, new HashSet<String>(Arrays.asList("Chemistry")));
        map.put(3455, new HashSet<String>(Arrays.asList("Chemistry", "History", "Mathematics")));
        map.put(56767, new HashSet<String>(Arrays.asList("Mathematics")));
        map.put(999, new HashSet<String>(Arrays.asList("Chemistry", "History", "Physics")));
        map.put(2834, new HashSet<String>(Arrays.asList("Physics")));
        map.put(323, new HashSet<String>(Arrays.asList("History", "Physics")));
        assertEquals(map, access.classesTaken(list));
    }

    @Test
    public void testStudentsRegistered() {
        StudentInfoAccess access = new StudentInfoAccess();
        List<Student> list = loadCSV();
        Map map = new HashMap<Integer, Set<Integer>>();
        map.put("Chemistry", new HashSet<Integer>(Arrays.asList(1234, 3455, 999)));
        map.put("History", new HashSet<Integer>(Arrays.asList(3455, 323, 999)));
        map.put("Mathematics", new HashSet<Integer>(Arrays.asList(56767, 3455)));
        map.put("Physics", new HashSet<Integer>(Arrays.asList(999, 2834, 323)));
        assertEquals(map, access.studentsRegistered(list));
    }

    @Test
    public void testMoreThanOneClass() {
        StudentInfoAccess access = new StudentInfoAccess();
        List<Student> list = loadCSV();
        assertEquals("3 : 323, 999, 3455", access.moreThanOneClass(list));
    }

    @Test
    public void testMoreThanOneClassProf() {
        StudentInfoAccess access = new StudentInfoAccess();
        List<Student> list = loadCSV();
        assertEquals("3 : Einstein, Smith, Jane", access.moreThanOneClassProf(list));
    }

    public List<Student> loadCSV() {
        try {
            //BufferedReader br = new BufferedReader(new FileReader(path.trim()));
            BufferedReader br = new BufferedReader(new InputStreamReader(StudentInfoAccessTest.class.getResourceAsStream("/testData.csv")));
            String row = "";
            List<Student> students = new ArrayList<Student>();
            br.readLine();
            while ((row = br.readLine()) != null) {
                String[] rowVals = row.split(",");
                if (rowVals.length == 3) {
                    ((ArrayList<Student>) students).add(new Student(rowVals[0].trim(), rowVals[1].trim(), Integer.parseInt(rowVals[2].trim())));
                } else if (rowVals.length == 2) {
                    ((ArrayList<Student>) students).add(new Student(rowVals[0].trim(), rowVals[1].trim(), 0));
                } else if (rowVals.length == 1) {
                    ((ArrayList<Student>) students).add(new Student(rowVals[0].trim(), "", 0));
                }

            }

            return students;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}