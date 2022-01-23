package com.proyecto.scraping.Services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.scraping.Beans.Pelicula;

@Service
public class PeliculaServiceImpl implements PeliculaService {

	@Autowired
	private PeliculaResporitory peliculaResporitory;

	public int getStatusConnectionCode(String url) {

		Response response = null;

		try {
			response = Jsoup.connect(url).userAgent("Chrome/51.0.2704.103").timeout(100000).ignoreHttpErrors(true)
					.execute();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el Status Code: " + ex.getMessage());
		}
		return response.statusCode();
	}

	public Document getHtmlDocument(String url) {

		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent("Chrome/51.0.2704.103").timeout(100000).get();
		} catch (IOException ex) {
			System.out.println("Excepción al obtener el HTML de la página" + ex.getMessage());
		}
		return doc;
	}

	@Override
	public void savePelicula(String url) {
		Pelicula pelicula = null;
		byte[] bytes = null;
		try {
			// Compruebo si me da un 200 al hacer la petición
			if (getStatusConnectionCode(url) == 200) {

				// Obtengo el HTML de la web en un objeto Document
				Document document = getHtmlDocument(url);

				// Busco todas las entradas que estan dentro de:
//            Elements entradas = document.select("div.col-md-4.col-xs-12").not("div.col-md-offset-2.col-md-4.col-xs-12");
				Elements peliculas = document.getElementsByClass("listado-peliculas-item");

				// Paseo cada una de las entradas
				for (Element elem : peliculas) {
					String titulo = elem.getElementsByClass("vf").text();
					String imagen = elem.getElementsByClass("cartel").attr("src").toString();
					String fecha = elem.attr("data-estreno");

					String comillas = Character.toString('"');

					if (titulo.contains("\"")) {
						titulo = titulo.replace("\"", "-");
					} else if (titulo.contains("/")) {
						titulo = titulo.replace("/", "-");
					} else if (titulo.contains(":")) {
						titulo = titulo.replace(":", "-");
					} else if (titulo.contains("*")) {
						titulo = titulo.replace("*", "-");
					} else if (titulo.contains("?")) {
						titulo = titulo.replace("?", "-");
					} else if (titulo.contains(comillas)) {
						titulo = titulo.replace(comillas, "-");
					} else if (titulo.contains("<")) {
						titulo = titulo.replace("<", "-");
					} else if (titulo.contains(">")) {
						titulo = titulo.replace(">", "-");
					} else if (titulo.contains("|")) {
						titulo = titulo.replace("|", "-");
					}

					bytes = Jsoup.connect(imagen).ignoreContentType(true).execute().bodyAsBytes();
//					System.out.println("bytes es =======>" + bytes);
					String encodedString = Base64.getEncoder().encodeToString(bytes);
//					System.out.println("EncodeString es =======>" + encodedString);
					// ESTO ES PARA CREAR EL FICHERO DE LA IMAGEN EN UNA CARPETA
//					ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//					BufferedImage bImage2 = ImageIO.read(bis);
//
//					ImageIO.write(bImage2, "jpg", new File("E:/ImagenesProyecto/" + titulo + ".jpg"));

					pelicula = new Pelicula();
					pelicula.setImagen(encodedString);
					pelicula.setNombre(titulo);
					Date fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
					pelicula.setFechaestreno(fecha2);

					System.out.println(
							pelicula.getNombre() + "\n" + pelicula.getFechaestreno() + "\n" + pelicula.getImagen());
					peliculaResporitory.save(pelicula);
					System.out.println("Pelicula Guardada => " + pelicula.getNombre());
				}

			} else {
				System.out.println("El Status Code no es OK es: " + getStatusConnectionCode(url));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("Fallo cambiar fecha");
		}
	}

}
