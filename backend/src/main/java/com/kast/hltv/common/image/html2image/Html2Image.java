package com.kast.hltv.common.image.html2image;

import com.kast.hltv.common.image.html2image.imagemap.HtmlImageMap;
import com.kast.hltv.common.image.html2image.imagemap.HtmlImageMapImpl;
import com.kast.hltv.common.image.html2image.parser.HtmlParser;
import com.kast.hltv.common.image.html2image.parser.HtmlParserImpl;
import com.kast.hltv.common.image.html2image.pdf.PdfRenderer;
import com.kast.hltv.common.image.html2image.pdf.PdfRendererImpl;
import com.kast.hltv.common.image.html2image.renderer.ImageRenderer;
import com.kast.hltv.common.image.html2image.renderer.ImageRendererImpl;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;

/**
 * @author Yoav Aharoni
 */
public class Html2Image {
	private HtmlParser parser = new HtmlParserImpl();
	private HtmlImageMap htmlImageMap;
	private ImageRenderer imageRenderer;
	private PdfRenderer pdfRenderer;

	public HtmlParser getParser() {
		return parser;
	}

	public HtmlImageMap getHtmlImageMap() {
		if (htmlImageMap == null) {
			htmlImageMap = new HtmlImageMapImpl(getImageRenderer());
		}
		return htmlImageMap;
	}

	public PdfRenderer getPdfRenderer() {
		if (pdfRenderer == null) {
			pdfRenderer = new PdfRendererImpl(parser);
		}
		return pdfRenderer;
	}

	public ImageRenderer getImageRenderer() {
		if (imageRenderer == null) {
			imageRenderer = new ImageRendererImpl(parser);
		}
		return imageRenderer;
	}

	public static @NotNull Html2Image fromDocument(Document document) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().setDocument(document);
		return html2Image;
	}

	public static @NotNull Html2Image fromHtml(String html) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().loadHtml(html);
		return html2Image;
	}

	public static @NotNull Html2Image fromURL(URL url) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(url);
		return html2Image;
	}

	public static @NotNull Html2Image fromURI(URI uri) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(uri);
		return html2Image;
	}

	public static @NotNull Html2Image fromFile(File file) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(file);
		return html2Image;
	}

	public static @NotNull Html2Image fromReader(Reader reader) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(reader);
		return html2Image;
	}

	public static @NotNull Html2Image fromInputStream(InputStream inputStream) {
		final Html2Image html2Image = new Html2Image();
		html2Image.getParser().load(inputStream);
		return html2Image;
	}
}
