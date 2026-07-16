package com.maxx.imperion.service;

import com.maxx.imperion.dto.CreateStudentRequestDto;
import com.maxx.imperion.dto.CreateStudentResponseDto;
import com.maxx.imperion.dto.UpdateStudentRequestDto;
import com.maxx.imperion.dto.UpdateStudentResponseDto;
import com.maxx.imperion.entity.Student;
import com.maxx.imperion.exception.DuplicateResourceException;
import com.maxx.imperion.exception.ResourceNotFoundException;
import com.maxx.imperion.repository.StudentRepository;
import com.maxx.imperion.response.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentService {

    //DI
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    // Constructor Injection
    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }


    public CreateStudentResponseDto createStudent(CreateStudentRequestDto studentReqDto) {
        Student student = modelMapper.map(studentReqDto,Student.class);
        // ModelMapper automatically maps fields with matching names and compatible data types.
        // Fields not present in the DTO are handled manually.
        student.setDeleted(false);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());


        if(emailExists(student)){
            throw new DuplicateResourceException("Student with email " + student.getEmail()
                    + " already exists");
        }

        Student savedStudent = studentRepository.save(student);

        CreateStudentResponseDto response =
                modelMapper.map(savedStudent ,CreateStudentResponseDto.class);
        response.setMessage("Student created successfully");

        return response;

    }

    public CreateStudentResponseDto getStudent(Long id) {
        Student studentResp = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        // ModelMapper automatically maps matching fields from Entity to Response DTO.
        CreateStudentResponseDto response =
                modelMapper.map(studentResp,CreateStudentResponseDto.class);
        response.setMessage("Student fetched successfully");

        return response;
    }

    //select * from student deleted = false;
    // findAByDeletedIsFalse
    public List<CreateStudentResponseDto> getAllStudent() {
        List<Student> studentList = studentRepository.findByDeletedIsFalse();

        return studentList.stream()
                .map(student -> {

                    // ModelMapper automatically maps matching fields from Entity to Response DTO.
                    CreateStudentResponseDto response =
                            modelMapper.map(student,CreateStudentResponseDto.class);

                    response.setMessage("Students fetched successfully");

                    return response;

                })
                .toList();
    }


    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {

        Student existingStudent = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found") );

        if (!existingStudent.getEmail().equals(studentReq.getEmail())
                && studentRepository.existsByEmail(studentReq.getEmail())) {

            throw new DuplicateResourceException(
                    "Student with email " + studentReq.getEmail() + " already exists");
        }

        existingStudent.setName(studentReq.getName());
        existingStudent.setAge(studentReq.getAge());
        existingStudent.setEmail(studentReq.getEmail());
        existingStudent.setRollno(studentReq.getRollno());
        existingStudent.setCourse(studentReq.getCourse());
        existingStudent.setSubjects(studentReq.getSubjects());
        existingStudent.setUpdatedAt(LocalDateTime.now());

        Student savedStudent = studentRepository.save(existingStudent);

        UpdateStudentResponseDto response =
                modelMapper.map(savedStudent,UpdateStudentResponseDto.class);

        response.setMessage("Student updated successfully");

        return response;
    }

    public ApiResponse<Void> deleteStudent(Long id) {

        Student studentToBeDeleted = studentRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        studentRepository.delete(studentToBeDeleted);

        return new ApiResponse<>(
                true,
                "Student deleted permanently.",
                null
        );
    }

    public ApiResponse<Void> deleteStudentSoft(Long id) {

        Student studentToBeDeleted = studentRepository
                .findByIdAndDeletedIsFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student with id " + id + " not found"));

        studentToBeDeleted.setDeleted(true);
        studentRepository.save(studentToBeDeleted);

        return new ApiResponse<>(
                true,
                "Student archived successfully.",
                null
        );
    }

    public ApiResponse<Void> restoreStudent(Long id) {

        Student student = studentRepository
                .findByIdAndDeletedIsTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Deleted student with id " + id + " not found"));

        student.setDeleted(false);
        studentRepository.save(student);

        return new ApiResponse<>(
                true,
                "Student restored successfully.",
                null
        );
    }

    public List<CreateStudentResponseDto> searchStudentByName(String name) {

        List<Student> students = studentRepository
                .findByNameContainingIgnoreCaseAndDeletedIsFalse(name);

        return students.stream()
                .map(student -> {
                    CreateStudentResponseDto response =
                            modelMapper.map(student, CreateStudentResponseDto.class);

                    response.setMessage("Students fetched successfully");

                    return response;
                })
                .toList();
    }

    public List<CreateStudentResponseDto> searchStudentByEmail(String email) {

        List<Student> students = studentRepository
                .findByEmailContainingIgnoreCaseAndDeletedIsFalse(email);

        return students.stream()
                .map(student -> {
                    CreateStudentResponseDto response =
                            modelMapper.map(student, CreateStudentResponseDto.class);

                    response.setMessage("Students fetched successfully");

                    return response;
                })
                .toList();
    }

    public List<CreateStudentResponseDto> searchStudentByCourse(String course) {

        List<Student> students = studentRepository
                .findByCourseContainingIgnoreCaseAndDeletedIsFalse(course);

        return students.stream()
                .map(student -> {
                    CreateStudentResponseDto response =
                            modelMapper.map(student, CreateStudentResponseDto.class);

                    response.setMessage("Students fetched successfully");

                    return response;
                })
                .toList();
    }

    public ApiResponse<Long> countStudents() {

        long count = studentRepository.countByDeletedIsFalse();

        return new ApiResponse<>(
                true,
                "Total active students fetched successfully.",
                count
        );
    }
    public ApiResponse<Long> countStudentsByCourse(String course) {

        long count = studentRepository.countByCourseIgnoreCaseAndDeletedIsFalse(course);

        return new ApiResponse<>(
                true,
                "Student count by course fetched successfully.",
                count
        );
    }
    public ApiResponse<Long> countStudentsByAge(int age) {

        long count = studentRepository.countByAgeAndDeletedIsFalse(age);

        return new ApiResponse<>(
                true,
                "Student count by age fetched successfully.",
                count
        );
    }


//    //Manual mapping methods
//    //create requestDto --> entity map  create
//    private Student mapToEntity(CreateStudentRequestDto studentReq){
//        Student student = new Student();
//        student.setName(studentReq.getName());
//        student.setAge(studentReq.getAge());
//        student.setEmail(studentReq.getEmail());
//        student.setRollno(studentReq.getRollno());
//        student.setSubject(studentReq.getSubject());
//        student.setDeleted(false); // by default false hi rahega
//        student.setCreatedAt(LocalDateTime.now());
//        student.setUpdatedAt(LocalDateTime.now());
//
//        return student;
//
//    }

//    //create entity  --> reponseDto  map
//    private CreateStudentResponseDto mapToDto (Student student){
//        CreateStudentResponseDto responseDto = new CreateStudentResponseDto();
//        responseDto.setId(student.getId());
//        responseDto.setName(student.getName());
//        responseDto.setAge(student.getAge());
//        responseDto.setEmail(student.getEmail());
//        responseDto.setSubject(student.getSubject());
//        responseDto.setRollno(student.getRollno());
//        responseDto.setMessage("Student Saved Successfully");
//        responseDto.setCreatedAt(student.getCreatedAt());
//        responseDto.setUpdatedAt(student.getUpdatedAt());
//        return responseDto;
//    }
//
//    private UpdateStudentResponseDto mapToUpdateDto(Student student){
//        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();
//        responseDto.setId(student.getId());
//        responseDto.setName(student.getName());
//        responseDto.setAge(student.getAge());
//        responseDto.setEmail(student.getEmail());
//        responseDto.setSubject(student.getSubject());
//        responseDto.setRollno(student.getRollno());
//        responseDto.setMessage("Student updated Successfully");
//        responseDto.setUpdatedAt(student.getUpdatedAt());
//        return responseDto;
//    }

    private boolean emailExists(Student student){
         return studentRepository.existsByEmail(student.getEmail());
    }


}
