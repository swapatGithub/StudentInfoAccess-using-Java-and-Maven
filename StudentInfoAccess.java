package com.company;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class StudentInfoAccess {

    public static void main(String[] args) {

        System.out.println("Enter the file path to load the data: ");
        String path = (new Scanner(System.in)).nextLine();
        //String pathOrig = path;
        //path = path.replace('/','\\');
        //System.out.println(" extension "+ path.substring(path.length()-4) +" and path "+path );
        //if (Pattern.matches("([a-zA-Z]:\\\\)?([a-zA-Z0-9\\s*_.-]+\\\\)*[a-zA-Z0-9\\s*_-]+.csv", path)) {
        if (path.substring(path.length() - 4).equalsIgnoreCase(".csv")) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(path.trim()));
                String row = "";
                List<Student> students = new ArrayList<Student>();
                br.readLine();
                while ((row = br.readLine()) != null) {
                    String[] rowVals = row.split(",");
                    if(rowVals.length==3) {
                        students.add(new Student(rowVals[0].trim(), rowVals[1].trim(), Integer.parseInt(rowVals[2].trim())));
                    }
                    else if (rowVals.length==2){
                        students.add(new Student(rowVals[0].trim(), rowVals[1].trim(), 0));
                    }
                    else if(rowVals.length==1){
                        students.add(new Student(rowVals[0].trim(), "", 0));
                    }
                }
                while (true) {
                    System.out.println("Select an option:\n1) List of class sections being taught\n2) List of classes being taken by each student\n" +
                            "3) Number of students registered for each class\n4) Number of students who have taken more than one class\n5) Number of professors" +
                            " who teach more than one class\n6) EXIT");
                    String opt = (new Scanner(System.in)).next();
                    switch (Integer.parseInt(opt)) {
                        case 1:
                            Map map = classesTaught(students);
                            for (Object entry : map.keySet()) {
                                String sub = entry.toString();
                                for(String prof : (Set<String>)map.get(sub)){
                                    System.out.println(sub+" , "+prof);
                                }
                            }
                            break;
                        case 2:
                            map = classesTaken(students);
                            for (Object entry : map.keySet()) {
                                int stu = Integer.parseInt(entry.toString());
                                System.out.println(stu+" : "+((Set<String>)map.get(stu)).toString().replaceAll("[\\[\\]]",""));
                            }
                            break;
                        case 3:
                            map = studentsRegistered(students);
                            for (Object entry : map.keySet()) {
                                String sub = entry.toString();
                                System.out.println(sub+" : "+((Set<Integer>)map.get(sub)).size());
                            }
                            break;
                        case 4:
                            System.out.println(moreThanOneClass(students));
                            break;
                        case 5:
                            System.out.println(moreThanOneClassProf(students));
                            break;
                        case 6:
                            System.exit(0);
                        default:
                            System.out.println("Invalid option");
                    }
                }
            }
            catch (FileNotFoundException ex){
                System.out.println("Invalid path or the file is not in the specified directory");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Provided file is not a .csv file, please execute again and provide a .csv file");
        }
    }
    public static Map classesTaught(List<Student> students){
        Map map = new HashMap<String,Set<String>>();
        for (Student student : students) {
            if (map.containsKey(student.getSubject())) {
                Set<String> vals = (Set<String>) map.get(student.getSubject());
                vals.add(student.getProf());
                map.put(student.getSubject(), vals);
            } else {
                Set<String> profs = new HashSet<String>();
                profs.add(student.getProf());
                map.put(student.getSubject(), profs);
            }
        }
        return map;
    }
    public static Map classesTaken(List<Student> students){
        Map map = new HashMap<Integer,Set<String>>();
        for (Student student : students) {
            if (map.containsKey(student.getS_id())) {
                Set<String> vals = (Set<String>) map.get(student.getS_id());
                vals.add(student.getSubject());
                map.put(student.getS_id(), vals);
            } else {
                Set<String> subs = new HashSet<String>();
                subs.add(student.getSubject());
                if(student.getS_id()!=0) {
                    map.put(student.getS_id(), subs);
                }
            }
        }
        return map;
    }
    public static Map studentsRegistered(List<Student> students){
        Map map = new HashMap<String,Set<Integer>>();
        for (Student student : students) {
            if (map.containsKey(student.getSubject())) {
                Set<Integer> vals = (Set<Integer>) map.get(student.getSubject());
                if(student.getS_id()!=0) {
                    vals.add(student.getS_id());
                    map.put(student.getSubject(), vals);
                }
            } else {
                Set<Integer> stus = new HashSet<Integer>();
                if(student.getS_id()!=0) {
                    stus.add(student.getS_id());
                    map.put(student.getSubject(), stus);
                }
            }
        }
        return map;
    }
    public static String moreThanOneClass(List<Student> students){
        Map map = new HashMap<Integer,Set<String>>();
        for (Student student : students) {
            if (map.containsKey(student.getS_id())) {
                Set<String> vals = (Set<String>) map.get(student.getS_id());
                vals.add(student.getSubject());
                map.put(student.getS_id(), vals);
            } else {
                Set<String> subs = new HashSet<String>();
                subs.add(student.getSubject());
                if(student.getS_id()!=0) {
                    map.put(student.getS_id(), subs);
                }
            }
        }
        int cnt = 0;
        StringBuffer ids = new StringBuffer();
        for (Object entry : map.keySet()) {
            int stu = Integer.parseInt(entry.toString());
            if (((Set<String>)map.get(stu)).size()>1){
                cnt++;
                ids.append(String.valueOf(stu)+", ");
            }
        }
        return cnt+" : "+ids.substring(0,ids.length()-2);
    }
    public static String moreThanOneClassProf(List<Student> students){
        Map map = new HashMap<String,Set<String>>();
        for (Student student : students) {
            if (map.containsKey(student.getProf())) {
                Set<String> vals = (Set<String>) map.get(student.getProf());
                vals.add(student.getSubject());
                map.put(student.getProf(), vals);
            } else {
                Set<String> subs = new HashSet<String>();
                subs.add(student.getSubject());
                map.put(student.getProf(), subs);
            }
        }
        int cnt = 0;
        StringBuffer profs = new StringBuffer();
        for (Object entry : map.keySet()) {
            String prof = entry.toString();
            if (((Set<String>)map.get(prof)).size()>1){
                cnt++;
                profs.append(prof+", ");
            }
        }
        return cnt+" : "+profs.substring(0,profs.length()-2);
    }
}

class Student{
    private String subject;
    private String prof;
    private int s_id;

    public Student(String sub, String prof, int id) {
        this.setSubject(sub);
        this.setProf(prof);
        this.setS_id(id);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

}