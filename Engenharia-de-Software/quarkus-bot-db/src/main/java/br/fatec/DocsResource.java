package br.fatec;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.ext.gfm.tables.TablesExtension;

@Path("/docs")
public class DocsResource {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String showDocumentation() throws IOException {

        //Caminho para o arquivo de documentação
        java.nio.file.Path docPath = java.nio.file.Path.of("DOCUMENTACAO.MD");

        //Lê o conteúdo do arquivo Markdown
        String markdown = Files.readString(docPath);

        //Adiciona suporte a tabelas em Markdown)
        List<Extension> extensions = List.of(TablesExtension.create());

        //Cria Parser e Renderer com extensões ativas
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();

        //Converte o Markdown em HTML
        String htmlContent = renderer.render(document);

        //Exibe a documentação em HTML renderizada no estilo Markdown
        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8">
                <title>Documentação - Quarkus AI Database Assistant</title>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/5.2.0/github-markdown.min.css">
                <style>
                    body {
                        display: flex;
                        justify-content: center;
                        background: #f6f8fa;
                        font-family: Arial, sans-serif;
                        padding: 40px;
                    }
                    .markdown-body {
                        box-shadow: 0 0 16px #0002;
                        border-radius: 12px;
                        background: #fff;
                        padding: 32px;
                        width: 80%;
                        max-width: 950px;
                    }
                    h1, h2, h3, h4 {
                        border-bottom: 1px solid #eee;
                        padding-bottom: 4px;
                    }
                    pre, code {
                        background: #f6f8fa;
                        border-radius: 6px;
                        padding: 4px 6px;
                        font-size: 0.95rem;
                    }
                    pre {
                        padding: 10px;
                        overflow-x: auto;
                    }
                    table {
                        border-collapse: collapse;
                        width: 100%;
                        margin: 16px 0;
                        font-size: 0.95rem;
                    }
                    th, td {
                        border: 1px solid #ddd;
                        padding: 8px 12px;
                        text-align: left;
                    }
                    th {
                        background: #f3f3f3;
                        font-weight: bold;
                    }
                    tr:nth-child(even) {
                        background: #fafafa;
                    }
                    a {
                        color: #0077cc;
                        text-decoration: none;
                    }
                    a:hover {
                        text-decoration: underline;
                    }
                </style>
            </head>
            <body>
                <article class="markdown-body">""" + htmlContent + """
                </article>
            </body>
            </html>
            """;
    }
}
