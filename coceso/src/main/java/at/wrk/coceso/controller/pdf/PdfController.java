package at.wrk.coceso.controller.pdf;

import at.wrk.coceso.entity.Concern;
import at.wrk.coceso.entity.Incident;
import at.wrk.coceso.entity.Operator;
import at.wrk.coceso.service.ConcernService;
import at.wrk.coceso.service.PdfService;
import at.wrk.coceso.utils.PdfDocument;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/pdf")
public class PdfController {

  private final static Logger LOG = Logger.getLogger(PdfController.class);

  @Autowired
  private PdfService pdfService;

  @Autowired
  private ConcernService concernService;

  @RequestMapping(value = "report", produces = "application/pdf", method = RequestMethod.GET)
  public void report(@RequestParam(value = "id") int id, @RequestParam(value = "fullDate", defaultValue = "0") boolean fullDate,
          HttpServletResponse response, UsernamePasswordAuthenticationToken token, Locale locale)
          throws ConcernNotFoundException {
    Operator user = (Operator) token.getPrincipal();
    Concern concern = concernService.getById(id);
    if (concern == null) {
      throw new ConcernNotFoundException();
    }

    LOG.info(String.format("User %s requested Final Report for Concern #%d (%s)", user.getUsername(), concern.getId(), concern.getName()));

    List<Incident> incidents = pdfService.getIncidents(concern.getId());
    PdfDocument document = null;
    try {
      document = pdfService.getDocument(PageSize.A4.rotate(), fullDate, response, locale);
      document.addFrontPage("label.pdf.report", concern, user);
      document.addStatistics(incidents);
      document.addCustomLog(concern.getId());
      document.addUnitsLog(concern.getId());
      document.addIncidentsLog(incidents);
      document.addLastPage();

      LOG.info(String.format("Final Report for Concern #%d (%s) completely written", concern.getId(), concern.getName()));
    } catch (IOException | DocumentException e) {
      LOG.error("FinalReportController.print(): " + e.getMessage());
    } finally {
      if (document != null) {
        document.close();
      }
    }
  }

  @RequestMapping(value = "dump", produces = "application/pdf", method = RequestMethod.GET)
  public void dump(@RequestParam(value = "id") int id, @RequestParam(value = "fullDate", defaultValue = "0") boolean fullDate,
          HttpServletResponse response, UsernamePasswordAuthenticationToken token, Locale locale)
          throws ConcernNotFoundException, ConcernClosedException {
    Operator user = (Operator) token.getPrincipal();
    Concern concern = concernService.getById(id);
    if (concern == null) {
      throw new ConcernNotFoundException();
    }
    if (concern.isClosed()) {
      throw new ConcernClosedException();
    }

    LOG.info(String.format("User %s requested PDF dump for Concern #%d (%s)", user.getUsername(), concern.getId(), concern.getName()));

    PdfDocument document = null;
    try {
      document = pdfService.getDocument(PageSize.A4.rotate(), fullDate, response, locale);
      document.addFrontPage("label.pdf.dump", concern, user);
      document.addUnitsCurrent(concern.getId());
      document.addIncidentsCurrent(concern.getId());
      document.addLastPage();

      LOG.info(String.format("PDF dump for Concern #%d (%s) completely written", concern.getId(), concern.getName()));
    } catch (IOException | DocumentException e) {
      LOG.error("FinalReportController.print(): " + e.getMessage());
    } finally {
      if (document != null) {
        document.close();
      }
    }
  }

  @RequestMapping(value = "transport.pdf", produces = "application/pdf", method = RequestMethod.GET)
  public void transport(@RequestParam(value = "id") int id, @RequestParam(value = "fullDate", defaultValue = "0") boolean fullDate,
          HttpServletResponse response, UsernamePasswordAuthenticationToken token, Locale locale)
          throws ConcernNotFoundException {
    Operator user = (Operator) token.getPrincipal();
    Concern concern = concernService.getById(id);
    if (concern == null) {
      throw new ConcernNotFoundException();
    }

    LOG.info(String.format("User %s requested transport list for Concern #%d (%s)", user.getUsername(), concern.getId(), concern.getName()));

    PdfDocument document = null;
    try {
      document = pdfService.getDocument(PageSize.A4, fullDate, response, locale);
      document.addFrontPage("label.pdf.transport", concern, user);
      document.addTransports(concern.getId());
      document.addLastPage();

      LOG.info(String.format("Transport list for Concern #%d (%s) completely written", concern.getId(), concern.getName()));
    } catch (IOException | DocumentException e) {
      LOG.error("FinalReportController.print(): " + e.getMessage());
    } finally {
      if (document != null) {
        document.close();
      }
    }
  }

  @ExceptionHandler(ConcernNotFoundException.class)
  public String notFoundError() {
    return "redirect:/home?error=1";
  }

  @ExceptionHandler(ConcernClosedException.class)
  public String closedError() {
    return "redirect:/home?error=1";
  }

  private class ConcernNotFoundException extends Exception {
  }

  private class ConcernClosedException extends Exception {
  }
}