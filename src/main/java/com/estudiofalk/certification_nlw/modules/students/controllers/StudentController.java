package com.estudiofalk.certification_nlw.modules.students.controllers;

import com.estudiofalk.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.estudiofalk.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.estudiofalk.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.estudiofalk.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.estudiofalk.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
  //preciso usar useCase (usar classe gerenciada pelo spring dentro da minha classe)
  //spring vai injetar a dependência, instanciar quando tiver que instanciar
  @Autowired
  private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

  @Autowired
  private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

  @PostMapping("/verifyIfHasCertification")
  public String verifyIfHasCertification(
    @RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO
  ) {
    var result =
      this.verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO);
    if (result) {
      return "Usuário já fez a prova";
    }
    return "Usuário pode fazer a prova";
  }

  @PostMapping("/certification/answer")
  public ResponseEntity<Object> certificationAnswer(
    @RequestBody StudentCertificationAnswerDTO studentCertificationAnswerDTO
  ) {
    try {
      var result =
        this.studentCertificationAnswersUseCase.execute(
            studentCertificationAnswerDTO
          );
      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
