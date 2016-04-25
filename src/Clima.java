import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import org.json.JSONObject;

public class Clima {

	public static void main(String[] args) {
		boolean continuar = true;
		do{
			Scanner lea = new Scanner (System.in);
			System.out.print("Ingrese el nombre de la ciudad: ");
			String ciudad = lea.nextLine();
			String noSpace = ciudad.replaceAll("\\s","");
			try {
				String respuesta = getHTML("http://api.openweathermap.org/data/2.5/weather?q=" + noSpace + "&appid=a93a69ffeb5005ae27e20c2f782378d7");
				
				JSONObject obj = new JSONObject(respuesta);
				//Temperatura
				System.out.println("En que deseara la temperatura. \n\t1.Celsius\n\t2.Fahrenheit\n\t3.Kelvin");
				int conversion = lea.nextInt();
				char conver;
				if	(conversion==1)
					conver = 'C';
				else if (conversion==2)
					conver = 'F';
				else conver = 'K';
				System.out.println("La temperatura en "+ciudad+" es: "+Temp(obj,respuesta,conversion)+"°"+conver);
				//Latitud y Longitud
				double lon =  obj.getJSONObject("coord").getDouble("lon");
				double lat =  obj.getJSONObject("coord").getDouble("lat");
				System.out.println(ciudad+" esta a "+lon+"° Longitud y a "+lat+"° Latitud.");
				//Presion Atmosferica
				int pression =  obj.getJSONObject("main").getInt("pressure");
				System.out.println("La presion atmosferica en "+ciudad+" es de: "+pression+"hPa.");
				//Humedad
				int humidity =  obj.getJSONObject("main").getInt("humidity");
				System.out.println("La humedad en "+ciudad+" es del "+humidity+"%.");
				//Otras opciones
				System.out.println ("Desea consultar otras opciones como:\n\t1. Velocidad del viento"
						+ "\n\t2. Salida del Sol\n\t3. Puesta del sol\n\t4. Todos");
				int otras = lea.nextInt();
				switch (otras){
					case 1:
						//Velocidad del viento
						double velo = obj.getJSONObject("wind").getDouble("speed");
						System.out.println("La velocidad del viento sera de: "+velo+"m/s");
						break;
					case 2:
						//Salida del sol
						long unixSeconds  = obj.getJSONObject("sys").getLong("sunrise");
						Date date = new Date(unixSeconds*1000L);
						System.out.println("La salida del sol sera a las : "+date);
						break;
					case 3:
						//Puesta del sol
						long unixSecond  = obj.getJSONObject("sys").getLong("sunset");
						Date dat = new Date(unixSecond*1000L);
						System.out.println("La puesta del sol sera a las : "+dat);
						break;
					case 4:
						//Todos
						double v = obj.getJSONObject("wind").getDouble("speed");
						System.out.println("La velocidad del viento sera de: "+v+"m/s");
						//Salida del sol
						long unixSecon  = obj.getJSONObject("sys").getLong("sunrise");
						Date da = new Date(unixSecon*1000L);
						System.out.println("La salida del sol sera a las : "+da);
						//Puesta del sol
						long unixSeco  = obj.getJSONObject("sys").getLong("sunset");
						Date d = new Date(unixSeco*1000L);
						System.out.println("La puesta del sol sera a las : "+d);
						break;
				}
				
				//long unixSeconds = 1372339860;
				//Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); // the format of your date
				//sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
				//String formattedDate = sdf.format(date);
				//System.out.println(date);
				//Continuar
				System.out.println ("Desea consultar el clima de otro lugar? y/n");
				char seguir = lea.next().charAt(0);
				if (seguir=='n')
					continuar= false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while (continuar);
		System.out.println("Nos vemos...");
	}
	
	public static String getHTML(String urlToRead) throws Exception {
	
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
		   result.append(line);
		}
		rd.close();
		return result.toString();
	}
	public static int Temp (JSONObject obj, String respuesta, int conversion){
		double temp = obj.getJSONObject("main").getDouble("temp");
		switch (conversion){
			case 1: 
				temp = temp - 273.15;
				break;
			case 2:
				temp = (temp-273.15)*1.800+32;
				break;
			case 3:
				break;
				
		}
		int tempInt=(int) temp;
		return tempInt; 
	}
	
}
