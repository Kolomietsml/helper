package academy.Motorola.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView entityNotFoundHandler(EntityNotFoundException ex) {
        var modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("message", ex.getMessage());
        return modelAndView;
    }
}
