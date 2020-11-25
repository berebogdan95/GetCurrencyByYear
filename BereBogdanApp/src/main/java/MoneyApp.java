import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MoneyApp {
	public static void main(String[] args) throws Exception {
		int growth = 0;
		int decrease = 0;
		int day = 0;
		Months arr[] = Months.values();
		Scanner scan = new Scanner(System.in);
		System.out.println("-----------------------------------------------");
		System.out.println("I taste the website and it works from year 2006");
		System.out.println("Don't work all currency like AFN,AMD,GEL etc");
		System.out.println("But surely will work with RON,EUR,GBP,USD,RUB");
		System.out.println("-----------------------------------------------");
        System.out.print("    Please insert the year: ");
		int year = scan.nextInt();
        System.out.print("    Please insert the currency from: ");
	    String currencyFrom= scan.next();
	    System.out.print("    Please insert the currency to: ");
	    String currencyTo= scan.next();
	    scan.close();
		System.out.println("----------------Please wait!-------------------");
		System.out.println();
		
		float aux = ReturnValue( "https://api.exchangeratesapi.io/" + year + "-" + 1 + "-" + 1 + "?symbols=" + currencyFrom.toUpperCase() + "&base=" + currencyTo.toUpperCase() );
		float minYear = ReturnValue( "https://api.exchangeratesapi.io/" + year + "-" + 1 + "-" + 1 + "?symbols=" + currencyFrom.toUpperCase() + "&base=" + currencyTo.toUpperCase() );
		float maxYear = ReturnValue( "https://api.exchangeratesapi.io/" + year + "-" + 1 + "-" + 1 + "?symbols=" + currencyFrom.toUpperCase() + "&base=" + currencyTo.toUpperCase() );
		for(int month = 1; month <= 12; month++) {
				day = MonthDays(month,year);
				float minMonth = ReturnValue( "https://api.exchangeratesapi.io/" + year + "-" + month + "-" + 1 + "?symbols=" + currencyFrom.toUpperCase() + "&base=" + currencyTo.toUpperCase() );
				float maxMonth = ReturnValue( "https://api.exchangeratesapi.io/" + year + "-" + month + "-" + 1 + "?symbols=" + currencyFrom.toUpperCase() + "&base=" + currencyTo.toUpperCase() );
			for(int zi = 1; zi <= day; zi++) {
				String url = "https://api.exchangeratesapi.io/" + year + "-" + month + "-" + zi + "?symbols=" + currencyFrom.toUpperCase() + "&base=" + currencyTo.toUpperCase();
				float value = ReturnValue(url);
				if(aux > value) {
					growth++;
				}
				if(aux < value) {
					decrease++;
				}
				if(maxYear < value) {
					maxYear = value;
				}
				if(minYear > value) {
					minYear = value;
				}
				if(maxMonth < value) {
					maxMonth = value;
				}
				if(minMonth > value) {
					minMonth = value;
				}
				
				aux = value;
			}
			System.out.println("           Calculated for "+arr[month-1]);
			System.out.println("        Minimum for "+arr[month-1]+" is "+ minMonth);
			System.out.println("        Maximum for "+arr[month-1]+" is "+ maxMonth);
			System.out.println();
		}
		System.out.println();
		System.out.println("-----Result for year " + year + " from " + currencyFrom.toUpperCase() + " to " + currencyTo.toUpperCase() + "------");
		System.out.println("	  Growth   counter: " + growth);
		System.out.println("	  Decrease counter: " + decrease);
		System.out.println("        Minimum for "+year+" is "+minYear);
		System.out.println("        Maximum for "+year+" is "+maxYear);
		System.out.println("-----------------------------------------------");
	}
	
	public static float ReturnValue(String url) throws Exception {
		float ronValue;
		Document doc = Jsoup.connect(url).ignoreContentType(true).get();
		String text = doc.body().text();
		StringBuilder strBuild = new StringBuilder();
		strBuild.append( text.charAt(16) );
		strBuild.append( text.charAt(17) );
		for(int i = 18; i < 30; i++) {
			boolean flag = Character.isDigit( text.charAt(i) );
			if(flag) {
				strBuild.append( text.charAt(i) );
			}
			else {
				break;
			}
		}
		ronValue = Float.parseFloat( strBuild.toString() );
		return ronValue;
	}
	
	public static int MonthDays(int month,int year){
		int days=0;
		if(month == 1) {
			days = 31;
		}
		if(month == 2) {
			if( (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0) ) {
				days = 29;
			}
			else {
				days = 28;
			}
		}
		if(month == 3) {
			days = 31;
		}
		if(month == 4) {
			days = 30;
		}
		if(month == 5) {
			days = 31;
		}
		if(month == 6) {
			days = 30;
		}
		if(month == 7) {
			days = 31;
		}
		if(month == 8) {
			days = 31;
		}
		if(month == 9) {
			days = 30;
		}
		if(month == 10) {
			days = 31;
		}
		if(month == 11) {
			days = 30;
		}
		if(month == 12) {
			days = 31;
		}
		return days;
	}
	
	enum Months 
	{ 
	    January, February, March, April, May, June, July, August, September, October, November, December; 
	} 
	
}
