package org.jfcutils.util;

/**
 * Maps various English-languages formats. 
 * <br>Currently supported languages are:
 * English, Spanish, French, Italian, German, Japanese, Thai, Chinese, Russian, Portuguese, Korean, Farsi, Latvian, Indonesian, Arabic
 * <br>Translation to 3-digits format also supports:
 * CZECH, SERBIAN, BULGARIAN, SWEDISH, HUNGARIAN, RUMENIAN, GEORGIAN, MOLDAVIAN, PILIPINO, SERBO-CROATIAN, FINNISH, HEBREW, CATALAN, LATIN, NORWEGIAN, 
 * VALENCIAN, POLISH, MALAY, TURKISH, SHONA
 * @author celli
 *
 */
public class LanguageTranslation {

	/**
	 * Maps languages to the 3 digit format
	 * @param docLanguage source language
	 * @return the language in the 3 digit format lowercased, null if no conversion
	 */
	public String translate2threedigitsEn(String docLanguage){
		String language = null;
		if(docLanguage!=null){
			if(docLanguage.length()!=3) {
				String l = docLanguage.toUpperCase();
				if (l.equals("EN") || l.equals("ENGLISH")) {
					language = "eng";
				} else if (l.equals("ES") || l.equals("SPANISH") || l.equals("SP")) {
					language = "esp";
				} else if (l.equals("FR") || l.equals("FRENCH")) {
					language = "fre";
				} else if (l.equals("IT") || l.equals("ITALIAN")) {
					language = "ita";
				} else if (l.equals("DE") || l.equals("GERMAN")) {
					language = "ger";
				} else if (l.equals("JA") || l.equals("JAPANESE")) {
					language = "jpn";
				} else if (l.equals("TH") || l.equals("THAI")) {
					language = "tha";
				} else if (l.equals("ZH") || l.equals("CHINESE") || l.equals("ZH_CN")) {
					language = "chi";
				} else if (l.equals("RU") || l.equals("RUSSIAN")) {
					language = "rus";
				} else if (l.equals("PT") || l.equals("PORTUGUESE")) {
					language = "por";
				} else if (l.equals("KO")) {
					language = "kor";
				} else if (l.equals("UA")) {
					language = "ukr";
				} else if (l.equals("FA") || l.equals("PERSIAN") || l.equals("FARSI")) {
					language = "per";
				} else if (l.equals("LV") || l.equals("LATVIAN")) {
					language = "lav";
				} else if (l.equals("ID") || l.equals("INDONESIAN") || l.equals("IN")) {
					language = "ind";
				} else if (l.equals("AR") || l.equals("ARABIC")) {
					language = "ara";
				} else if (l.equals("NL") || l.equals("DUTCH")) {
					language = "dut";
				} else if (l.equals("SK") || l.equals("SLOVAK")) {
					language = "slo";
				} else if (l.equals("DA") || l.equals("DANISH")) {
					language = "dan";
				} else if (l.equals("CS") || l.equals("CZECH")) {
					language = "cze";
				} else if (l.equals("SR") || l.equals("SERBIAN")) {
					language = "srp";
				} else if (l.equals("BG") || l.equals("BULGARIAN")) {
					language = "bul";
				} else if (l.equals("SV") || l.equals("SWEDISH")) {
					language = "swe";
				} else if (l.equals("HU") || l.equals("HUNGARIAN")) {
					language = "hun";
				} else if (l.equals("ROMANIAN") || l.equals("RUMENIAN") || l.equals("MOLDAVIAN") || l.equals("RO")) {
					language = "rum";
				} else if (l.equals("KA") || l.equals("GEORGIAN")) {
					language = "kat";
				} else if (l.equals("FILIPINO") || l.equals("PILIPINO")) {
					language = "fil";
				} else if (l.equals("SH") || l.equals("SERBO-CROATIAN")) {
					language = "scr";
				} else if (l.equals("FI") || l.equals("FINNISH")) {
					language = "fin";
				} else if (l.equals("HE") || l.equals("HEBREW")) {
					language = "heb";
				} else if (l.equals("CA") || l.equals("CATALAN") || l.equals("VALENCIAN")) {
					language = "cat";
				} else if (l.equals("LA") || l.equals("LATIN")) {
					language = "lat";
				} else if (l.equals("NO") || l.equals("NORWEGIAN")) {
					language = "nor";
				} else if (l.equals("PL") || l.equals("POLISH")) {
					language = "pol";
				} else if (l.equals("MS") || l.equals("MALAY")) {
					language = "may";
				} else if (l.equals("TR") || l.equals("TURKISH")) {
					language = "tur";
				} else if (l.equals("SN") || l.equals("SHONA")) {
					language = "sna";
				} else if (l.equals("NULL")) {
					language = null;
				}
			}
			else {
				return docLanguage;
			}
		}
		return language;
	}
	
	/**
	 * Maps two-three digits languages to the entire languages
	 * @param docLanguage source language
	 * @return the language expressed in the extended form, the original language if no conversion
	 */
	public String translate2ExtendedEnglish(String docLanguage){
		if(docLanguage!=null && docLanguage.length()<=3){
			String l = docLanguage.toUpperCase();
			String language;
			if (l.equals("EN") || l.equals("ENG")) {
				language = "English";
			} else if (l.equals("ES") || l.equals("ESP") || l.equals("SPA")) {
				language = "Spanish";
			} else if (l.equals("FR") || l.equals("FRA") || l.equals("FRE")) {
				language = "French";
			} else if (l.equals("IT") || l.equals("ITA")) {
				language = "Italian";
			} else if (l.equals("DE") || l.equals("GER")) {
				language = "German";
			} else if (l.equals("JA") || l.equals("JPN")) {
				language = "Japanese";
			} else if (l.equals("TH") || l.equals("THA")) {
				language = "Thai";
			} else if (l.equals("ZH") || l.equals("CHI")) {
				language = "Chinese";
			} else if (l.equals("RU") || l.equals("RUS")) {
				language = "Russian";
			} else if (l.equals("PT") || l.equals("POR")) {
				language = "Portuguese";
			} else if (l.equals("KO") || l.equals("KOR")) {
				language = "Korean";
			} else if (l.equals("UA") || l.equals("UKR")) {
				language = "Ukrainian";
			} else if (l.equals("FA") || l.equals("FAS") || l.equals("PER")) {
				language = "Farsi";
			} else if (l.equals("LV") || l.equals("LAV")) {
				language = "Latvian";
			} else if (l.equals("IND") || l.equals("IN") || l.equals("ID")) {
				language = "Indonesian";
			} else if (l.equals("AR") || l.equals("ARA")) {
				language = "Arabic";
			} else if (l.equals("POL")|| l.equals("PL")) {
				language = "Polish";
			} else if (l.equals("RUM")|| l.equals("RO")) {
				language = "Romanian";
			} else if (l.equals("SLV")|| l.equals("SL")) {
				language = "Slovenian";
			} else if (l.equals("DUT")|| l.equals("NL")) {
				language = "Dutch";
			} else if (l.equals("CZE")|| l.equals("CS")) {
				language = "Czech";
			} else if (l.equals("NOR")|| l.equals("NO")) {
				language = "Norwegian";
			} else if (l.equals("SLO")|| l.equals("SK")) {
				language = "Slovak";
			} else if (l.equals("HUN")|| l.equals("HU")) {
				language = "Hungarian";
			} else if (l.equals("SWE")|| l.equals("SV")) {
				language = "Swedish";
			} else if (l.equals("DAN")|| l.equals("DA")) {
				language = "Danish";
			} else if (l.equals("BUL")|| l.equals("BG")) {
				language = "Bulgarian";
			} else if (l.equals("SCR")|| l.equals("SH")) {
				language = "Serbo-Croatian";
			} else {
				language = docLanguage;
			}
			return language;
		}
		return docLanguage;
	}

}
