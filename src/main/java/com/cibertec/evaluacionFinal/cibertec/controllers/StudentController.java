package com.cibertec.evaluacionFinal.cibertec.controllers;


import com.cibertec.evaluacionFinal.cibertec.dtos.StudentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class StudentController {

    //Variable ruta de la API definido en properties
    @Value("${backend.url}")
    private String apiUrl;

    //DI
    private final RestTemplate restTemplate;

    //Constructor
    public StudentController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .rootUri(this.apiUrl)
                .build();
    }


    //vista para listar estudiantes
    @GetMapping("/")
    public String index(Model model) {
        //Peticion Get a la API
        ResponseEntity<StudentDTO[]> students = this.restTemplate
                .getForEntity(this.apiUrl, StudentDTO[].class);
        model.addAttribute("students", students.getBody());
        return "index";
    }

    // Metodo para obtener un producto específico por su ID
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        // Realiza una petición GET a la API
        ResponseEntity<StudentDTO> student =
                this.restTemplate.getForEntity(
                        this.apiUrl + '/' + id, StudentDTO.class);

        // Añade el producto obtenido al modelo para que sea enviado y muestre en la vista de detalles
        model.addAttribute("student", student.getBody());
        return "details-student";
    }



    //Vista del formulario
    @GetMapping("/new")
    public String newStudent(Model model) {
        studentDetails(model, "Register Student" , "Register","/save",  new StudentDTO() );
        /*
        model.addAttribute("title", "Register Student");
        model.addAttribute("textButton", "Register");
        model.addAttribute("action", "/save");
        model.addAttribute("student", new StudentDTO()); // Crea un nuevo objeto vacío de ProductDTO para ser utilizado en el formulario
        */
        return "formStudent";
    }

    // Metodo para guardar un nuevo estudiante
    @PostMapping("/save")
    public String save(StudentDTO studentDTO) {
        //Consultar al backend para guardar
        this.restTemplate.postForEntity
                (this.apiUrl,
                        studentDTO
                        , StudentDTO.class);

        return "redirect:/";
    }


    // Metodo para mostrar el formulario de edición de un producto existente
    @GetMapping("/edit/{id}")
    public String showFormToEdit(@PathVariable Long id, Model model) {
        ResponseEntity<StudentDTO> student = this.restTemplate
                .getForEntity(this.apiUrl + '/' + id, StudentDTO.class);

        studentDetails(model,"Edit Student", "/save",  "Save", student.getBody());
        /*model.addAttribute("title", "Edit Student");
        model.addAttribute("action", "/save");
        model.addAttribute("textButton", "Save");
        model.addAttribute("student", student.getBody());*/

        // Añade el producto al modelo para que se cargue en el formulario
        return "formStudent";
    }


    // Metodo para mostrar el formulario de eliminación de un producto
    @GetMapping("/viewdelete/{id}")
    public String showFormToDelete(@PathVariable Long id, Model model) {
        // Realiza una solicitud GET para obtener el producto a eliminar
        ResponseEntity<StudentDTO> student = this.restTemplate
                .getForEntity(this.apiUrl + '/' + id, StudentDTO.class);
        studentDetails(model,"Delete Student", "/delete/" + student.getBody().getId(),  "Delete", student.getBody());
          /*
            model.addAttribute("title", "Delete Student");
            model.addAttribute("action", "/delete/" + student.getBody().getId());
            model.addAttribute("textButton", "Delete");
            model.addAttribute("student", student.getBody()); // Añade el producto al modelo para mostrar sus datos en el formulario
            */
        return "formStudent";
    }

    // Metodo para eliminar un producto
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        // Realiza una solicitud DELETE a la API
        this.restTemplate
                .delete(this.apiUrl + '/' + id );  // Llama al metodo DELETE con su endpoint establecido
        return "redirect:/";
    }

    /* Additional methods */
    void studentDetails (Model model,  String title, String action, String textButton , StudentDTO student) {
        model.addAttribute("title", "Delete Student");
        model.addAttribute("action", action);
        model.addAttribute("textButton", "Delete");
        model.addAttribute("student",student); // Añade el Student al modelo para mostrar sus datos en el formulario
    }

    void get(Long id){
        ResponseEntity<StudentDTO> student =
                this.restTemplate.getForEntity(
                        this.apiUrl + '/' + id,
                        StudentDTO.class);
    }

}


