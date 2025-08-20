package com.cadastramento.radiador.service;

import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class PdfGenerationService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Gera um PDF a partir de um template Thymeleaf.
     * @param templateName O nome do arquivo de template (ex: "relatorio-semana").
     * @param modelAttributes Um mapa com os atributos a serem passados para o template.
     * @return Um ByteArrayOutputStream contendo os bytes do PDF gerado.
     */
    public ByteArrayOutputStream generatePdfFromTemplate(String templateName, Map<String, Object> modelAttributes) {
        Context context = new Context();
        context.setVariables(modelAttributes);

        String html = templateEngine.process(templateName, context);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            // Use Spring's ResourceLoader for a more robust way to find the base path.
            // This still requires the 'src/main/resources/static/' directory to exist.
            Resource resource = resourceLoader.getResource("classpath:static/");
            String baseUrl = resource.getURL().toExternalForm();
            renderer.getSharedContext().setBaseURL(baseUrl);

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream;
        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Erro ao gerar o PDF a partir do template: " + templateName, e);
        }
    }
}