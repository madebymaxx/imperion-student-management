package com.maxx.imperion.controller;

import com.maxx.imperion.dto.CreateStudentRequestDto;
import com.maxx.imperion.dto.CreateStudentResponseDto;
import com.maxx.imperion.dto.UpdateStudentRequestDto;
import com.maxx.imperion.dto.UpdateStudentResponseDto;
import com.maxx.imperion.response.ApiResponse;
import com.maxx.imperion.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/students")
@Tag(
        name = "Student Management",
        description = "REST APIs for Student Management System"
)
public class StudentController {

    //DI
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //create student
    @PostMapping
    @Operation(
            summary = "Create Student",
            description = "Creates a new student with course and multiple subjects."
    )
    public ResponseEntity<CreateStudentResponseDto> createStudent(
            @Valid @RequestBody CreateStudentRequestDto createStudentRequestDto){
        CreateStudentResponseDto createdStudent =
                studentService.createStudent(createStudentRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStudent);
    }

    // read one student
    @GetMapping("/{id}")
    @Operation(
            summary = "Get Student By ID",
            description = "Fetches an active student using the student ID."
    )
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id){
        CreateStudentResponseDto studentResp = studentService.getStudent(id);

            return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }

    // read all student
    @GetMapping
    @Operation(
            summary = "Get All Students",
            description = "Returns all active students."
    )
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
        List<CreateStudentResponseDto> studentList = studentService.getAllStudent();

        return ResponseEntity.ok(studentList);
    }

    //update student
    @PutMapping("/{id}")
    @Operation(
            summary = "Update Student",
            description = "Updates student details."
    )
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@PathVariable Long id,
                                                @Valid @RequestBody UpdateStudentRequestDto studentReq){
        UpdateStudentResponseDto studentResp =
                studentService.updateStudent(id,studentReq);

        return ResponseEntity.ok(studentResp);
    }
    //delete student
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete Student",
            description = "Permanently deletes a student from the database."
    )
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {

        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @PatchMapping("/soft-delete/{id}")
    @Operation(
            summary = "Soft Delete Student",
            description = "Archives a student without permanently deleting the record."
    )
    public ResponseEntity<ApiResponse<Void>> softDeleteStudent(@PathVariable Long id) {

        return ResponseEntity.ok(studentService.deleteStudentSoft(id));
    }

    @PatchMapping("/restore/{id}")
    @Operation(
            summary = "Restore Student",
            description = "Restores an archived student."
    )
    public ResponseEntity<ApiResponse<Void>> restoreStudent(@PathVariable Long id) {

        return ResponseEntity.ok(studentService.restoreStudent(id));
    }

    @GetMapping("/search/name")
    @Operation(
            summary = "Search Student By Name",
            description = "Searches active students using a partial or full name."
    )
    public ResponseEntity<List<CreateStudentResponseDto>> searchStudentByName(
            @RequestParam String name) {

        return ResponseEntity.ok(studentService.searchStudentByName(name));
    }

    @GetMapping("/search/email")
    @Operation(
            summary = "Search Student By Email",
            description = "Searches active students using email."
    )
    public ResponseEntity<List<CreateStudentResponseDto>> searchStudentByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(studentService.searchStudentByEmail(email));
    }

    @GetMapping("/search/course")
    @Operation(
            summary = "Search Student By Course",
            description = "Searches active students using course name."
    )
    public ResponseEntity<List<CreateStudentResponseDto>> searchStudentByCourse(
            @RequestParam String course) {

        return ResponseEntity.ok(studentService.searchStudentByCourse(course));
    }

    @GetMapping("/count")
    @Operation(
            summary = "Count Students",
            description = "Returns the total number of active students."
    )
    public ResponseEntity<ApiResponse<Long>> countStudents() {
        return ResponseEntity.ok(studentService.countStudents());
    }

    @GetMapping("/count/course")
    @Operation(
            summary = "Count Students By Course",
            description = "Returns the total number of active students in a course."
    )
    public ResponseEntity<ApiResponse<Long>> countStudentsByCourse(
            @RequestParam String course) {

        return ResponseEntity.ok(studentService.countStudentsByCourse(course));
    }

    @GetMapping("/count/age")
    @Operation(
            summary = "Count Students By Age",
            description = "Returns the total number of active students of a given age."
    )
    public ResponseEntity<ApiResponse<Long>> countStudentsByAge(
            @RequestParam int age) {

        return ResponseEntity.ok(studentService.countStudentsByAge(age));
    }


}
