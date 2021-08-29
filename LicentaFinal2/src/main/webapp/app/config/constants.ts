const config = {
  VERSION: process.env.VERSION,
};

export default config;

export const SERVER_API_URL = process.env.SERVER_API_URL;

export const AUTHORITIES = {
  ADMIN: 'ROLE_ADMIN',
  USER: 'ROLE_USER',
  ANONYMOUS:'ROLE_ANONYMOUS'
};

export const messages = {
  DATA_ERROR_ALERT: 'Internal Error',
};

export const APP_DATE_FORMAT = 'DD/MM/YY HH:mm';
export const APP_TIMESTAMP_FORMAT = 'DD/MM/YY HH:mm:ss';
export const APP_LOCAL_DATE_FORMAT = 'DD/MM/YYYY';
export const APP_LOCAL_DATETIME_FORMAT = 'YYYY-MM-DDTHH:mm';
export const APP_LOCAL_DATETIME_FORMAT_Z = 'YYYY-MM-DDTHH:mm Z';
export const APP_WHOLE_NUMBER_FORMAT = '0,0';
export const APP_TWO_DIGITS_AFTER_POINT_NUMBER_FORMAT = '0,0.[00]';

export const judet = [
  {id:1,name: "Alba",city:[
    {id:1,name:"Abrud"},
    {id:2,name:"Aiud"},
    {id:3,name:"Alba Iulia"},
    {id:4,name:"Baia de Aries"},
    {id:5,name:"Blaj"},
    {id:6,name:"Cugir"},
    {id:7,name:"Campeni"},
    {id:8,name:"Ocna Mures"},
    {id:9,name:"Sebes"},
    {id:10,name:"Teius"},
    {id:11,name:"Zlatna"}
  ]},
  {id:2,name:"Arad",city:[
    {id:1,name:"Arad"},
    {id:2,name:"Chisineu-Cris"},
    {id:3,name:"Curtici"},
    {id:4,name:"Ineu"},
    {id:5,name:"Lipova"},
    {id:6,name:"Nadlac"},
    {id:7,name:"Pecica"},
    {id:8,name:"Pancota"},
    {id:9,name:"Sebis"},
    {id:10,name:"Santana"}
  ]},
{id:3,name: "Arges",city:[
    {id:1,name:"Campulung-Muscel"},
    {id:2,name:"Costesti"},
    {id:3,name:"Curtea de Arges"},
    {id:4,name:"Mioveni"},
    {id:5,name:"Pitesti"},
    {id:6,name:"Stefanesti"},
    {id:7,name:"Topoloveni"}
  ]},
{id:4,name:"Bacau",city:[
    {id:1,name:"Bacau"},
    {id:2,name:"Buhusi"},
    {id:3,name:"Comanesti"},
    {id:4,name:"Darmanesti"},
    {id:5,name:"Moinesti"},
    {id:6,name:"Onesti"},
    {id:7,name:"Slanic-Moldova"},
    {id:8,name:"Targu Ocna"}
  ]},
{id:5,name:"Bihor",city:[
    {id:1,name:"Alesd"},
    {id:2,name:"Beius"},
    {id:3,name:"Marghita"},
    {id:4,name:"Nucet"},
    {id:5,name:"Oradea"},
    {id:6,name:"Salonta"},
    {id:7,name:"Sacuieni"},
    {id:8,name:"Stei"},
    {id:9,name:"Valea lui Mihai"},
    {id:10,name:"Vascau"}
  ]},
{id:6,name:"Bistrita-Nasaud",city:[
    {id:1,name:"Beclean"},
    {id:2,name:"Bistrita"},
    {id:3,name:"Nasaud"},
    {id:4,name:"Sangeorz-Bai"}
  ]},
{id:7,name:"Botosani",city:[
    {id:1,name:"Botosani"},
    {id:2,name:"Bucecea"},
    {id:3,name:"Darabani"},
    {id:4,name:"Dorohoi"},
    {id:5,name:"Flamanzi"},
    {id:6,name:"Saveni"},
    {id:7,name:"Stefanesti"}
  ]},
{id:8,name:"Brasov",city:[
    {id:1,name:"Brasov"},
    {id:2,name:"Codlea"},
    {id:3,name:"Fagaras"},
    {id:4,name:"Ghimbav"},
    {id:5,name:"Predeal"},
    {id:6,name:"Rasnov"},
    {id:7,name:"Rupea"},
    {id:8,name:"Sacele"},
    {id:9,name:"Victoria"},
    {id:10,name:"Zarnesti"},
  ]},
{id:9,name:"Braila",city:[
    {id:1,name:"Braila"},
    {id:2,name:"Faurei"},
    {id:3,name:"Ianca"},
    {id:4,name:"Insuratei"},
  ]},
{id:10,name:"Bucuresti",city:[
    {id:1,name:"Bucuresti"}
  ]},
{id:11,name:"Buzau",city:[
    {id:1,name:"Buzau"},
    {id:2,name:"Nehoiu"},
    {id:3,name:"Pogoanele"},
    {id:4,name:"Patirlagele"},
    {id:5,name:"Ramnicu Sarat"}
  ]},
{id:12,name:"Caras-Severin",city:[
    {id:1,name:"Anina"},
    {id:2,name:"Baile Herculane"},
    {id:3,name:"Bocsa"},
    {id:4,name:"Caransebes"},
    {id:5,name:"Moldova Noua"},
    {id:6,name:"Oravita"},
    {id:7,name:"Otelu Rosu"},
    {id:8,name:"Resita"}
  ]},
{id:13,name:"Calarasi",city:[
    {id:1,name:"Budesti"},
    {id:2,name:"Calarasi"},
    {id:3,name:"Fundulea"},
    {id:4,name:"Lehliu Gara"},
    {id:5,name:"Oltenita"}
  ]},
{id:14,name:"Cluj",city:[
    {id:1,name:"Campia Turzii"},
    {id:2,name:"Cluj-Napoca"},
    {id:3,name:"Dej"},
    {id:4,name:"Gherla"},
    {id:5,name:"Huedin"},
    {id:6,name:"Turda"}
  ]},
{id:15,name:"Constanta",city:[
    {id:1,name:"Baneasa"},
    {id:2,name:"Cernavoda"},
    {id:3,name:"Constanta"},
    {id:4,name:"Eforie"},
    {id:5,name:"Harsova"},
    {id:6,name:"Mangalia"},
    {id:7,name:"Medgidia"},
    {id:8,name:"Murfatlar"},
    {id:9,name:"Navodari"},
    {id:10,name:"Negru Voda"},
    {id:11,name:"Ovidiu"},
    {id:12,name:"Techirghiol"}
  ]},
{id:16,name:"Covasna",city:[
    {id:1,name:"Baraolt"},
    {id:2,name:"Covasna"},
    {id:3,name:"Intorsura Buzaului"},
    {id:4,name:"Sfantu Gheorghe"},
    {id:5,name:"Targu Secuiesc"}
  ]},
{id:17,name:"Dambovita",city:[
    {id:1,name:"Fieni"},
    {id:2,name:"Gaesti"},
    {id:3,name:"Moreni"},
    {id:4,name:"Pucioasa"},
    {id:5,name:"Racari"},
    {id:6,name:"Targoviste"},
    {id:7,name:"Titu"}
  ]},
{id:18,name:"Dolj",city:[
    {id:1,name:"Bailesti"},
    {id:2,name:"Bechet"},
    {id:3,name:"Calafat"},
    {id:4,name:"Craiova"},
    {id:5,name:"Dabuleni"},
    {id:6,name:"Filiasi"},
    {id:7,name:"Segarcea"}
  ]},
{id:19,name:"Galati",city:[
    {id:1,name:"Beresti"},
    {id:2,name:"Galati"},
    {id:3,name:"Targu Bujor"},
    {id:4,name:"Tecuci"}
  ]},
{id:20,name:"Giurgiu",city:[
    {id:1,name:"Bolintin-Vale"},
    {id:2,name:"Giurgiu"},
    {id:3,name:"Mihailesti"}
  ]},
{id:21,name:"Gorj",city:[
    {id:1,name:"Bumbesti-Jiu"},
    {id:2,name:"Motru"},
    {id:3,name:"Novaci"},
    {id:4,name:"Rovinari"},
    {id:5,name:"Tismana"},
    {id:6,name:"Targu Carbunesti"},
    {id:7,name:"Targu Jiu"},
    {id:8,name:"Ticleni"},
    {id:9,name:"Turceni"}
  ]},
{id:22,name:"Harghita",city:[
    {id:1,name:"Baile Tusnad"},
    {id:2,name:"Balan"},
    {id:3,name:"Borsec"},
    {id:4,name:"Cristuru Secuiesc"},
    {id:5,name:"Gheorgheni"},
    {id:6,name:"Miercurea-Ciuc"},
    {id:7,name:"Odorheiu Secuiesc"},
    {id:8,name:"Toplita"},
    {id:9,name:"Vlahita"}
  ]},
{id:23,name:"Hunedoara",city:[
    {id:1,name:"Anisoara"},
    {id:2,name:"Brad"},
    {id:3,name:"Calan"},
    {id:4,name:"Deva"},
    {id:5,name:"Geoagiu"},
    {id:6,name:"Hateg"},
    {id:7,name:"Hunedoara"},
    {id:8,name:"Lupeni"},
    {id:9,name:"Orastie"},
    {id:10,name:"Petrila"},
    {id:11,name:"Petrosani"},
    {id:12,name:"Simeria"},
    {id:13,name:"Uricani"},
    {id:14,name:"Vulcan"}
  ]},
{id:24,name:"Ialomita",city:[
    {id:1,name:"Amara"},
    {id:2,name:"Cazanesti"},
    {id:3,name:"Fetesti"},
    {id:4,name:"Fierbinti-Tirg"},
    {id:5,name:"Slobozia"},
    {id:6,name:"Tandarei"},
    {id:7,name:"Urziceni"}
  ]},
{id:25,name:"Iasi",city:[
    {id:1,name:"Harlau"},
    {id:2,name:"Iasi"},
    {id:3,name:"Pascani"},
    {id:4,name:"Podu Iloaiei"},
    {id:5,name:"Targu Frumos"}
  ]},
{id:26,name:"Ilfov",city:[
    {id:1,name:"Bragadiru"},
    {id:2,name:"Buftea"},
    {id:3,name:"Chitila"},
    {id:4,name:"Magurele"},
    {id:5,name:"Otopeni"},
    {id:6,name:"Pantelimon"},
    {id:7,name:"Popesti-Leordeni"},
    {id:8,name:"Voluntari"}
  ]},
{id:27,name:"Maramures",city:[
    {id:1,name:"Baia Mare"},
    {id:2,name:"Baia Sprie"},
    {id:3,name:"Borsa"},
    {id:4,name:"Cavnic"},
    {id:5,name:"Dragomiresti"},
    {id:6,name:"Salistea de Sus"},
    {id:7,name:"Seini"},
    {id:8,name:"Sighetu Marmatiei"},
    {id:9,name:"Somcuta Mare"},
    {id:10,name:"Ulmeni"},
    {id:11,name:"Targu Lapus"},
    {id:12,name:"Tautii Magheraus"},
    {id:13,name:"Viseu de Sus"}
  ]},
{id:28,name:"Mehedinti",city:[
    {id:1,name:"Baia de Arama"},
    {id:2,name:"Drobeta-Turnu Severin"},
    {id:3,name:"Orsova"},
    {id:4,name:"Strehaia"},
    {id:5,name:"Vanju Mare"}
  ]},
{id:29,name:"Mures",city:[
    {id:1,name:"Iernut"},
    {id:2,name:"Ludus"},
    {id:3,name:"Miercurea Nirajului"},
    {id:4,name:"Reghin"},
    {id:5,name:"Sangeorgiu de Padure"},
    {id:6,name:"Sarmasu"},
    {id:7,name:"Sighisoara"},
    {id:8,name:"Sovata"},
    {id:9,name:"Targu Mures"},
    {id:10,name:"Tarnaveni"},
    {id:11,name:"Ungheni"}
  ]},
{id:30,name:"Neamt",city:[
    {id:1,name:"Bicaz"},
    {id:2,name:"Piatra Neamt"},
    {id:3,name:"Roman"},
    {id:4,name:"Roznov"},
    {id:5,name:"Targu Neamt"}
  ]},
{id:31,name:"Olt",city:[
    {id:1,name:"Bals"},
    {id:2,name:"Caracal"},
    {id:3,name:"Corabia"},
    {id:4,name:"Draganesti-Olt"},
    {id:5,name:"Piatra-Olt"},
    {id:6,name:"Potcoava"},
    {id:7,name:"Scornicesti"},
    {id:8,name:"Slatina"}
  ]},
{id:32,name:"Prahova",city:[
    {id:1,name:"Azuga"},
    {id:2,name:"Baicoi"},
    {id:3,name:"Boldesti Scaieni"},
    {id:4,name:"Breaza"},
    {id:5,name:"Busteni"},
    {id:6,name:"Campina"},
    {id:7,name:"Comarnic"},
    {id:8,name:"Mizil"},
    {id:9,name:"Ploiesti"},
    {id:10,name:"Plopeni"},
    {id:11,name:"Sinaia"},
    {id:12,name:"Slanic"},
    {id:13,name:"Urlati"},
    {id:14,name:"Valenii de Munte"}
  ]},
{id:33,name:"Satu Mare",city:[
    {id:1,name:"Ardud"},
    {id:2,name:"Carei"},
    {id:3,name:"Livada"},
    {id:4,name:"Negresti-Oas"},
    {id:5,name:"Satu Mare"},
    {id:6,name:"Tasnad"}
  ]},
{id:34,name:"Salaj",city:[
    {id:1,name:"Cehu Silvaniei"},
    {id:2,name:"Jibou"},
    {id:3,name:"Simleu Silvaniei"},
    {id:4,name:"Zalau"}
  ]},
{id:35,name:"Sibiu",city:[
    {id:1,name:"Agnita"},
    {id:2,name:"Avrig"},
    {id:3,name:"Cisnadie"},
    {id:4,name:"Copsa Mica"},
    {id:5,name:"Dumbraveni"},
    {id:6,name:"Medias"},
    {id:7,name:"Miercurea Sibiului"},
    {id:8,name:"Ocna Sibiului"},
    {id:9,name:"Saliste"},
    {id:10,name:"Sibiu"},
    {id:11,name:"Talmaciu"}
  ]},
{id:36,name:"Suceava",city:[
    {id:1,name:"Brosteni"},
    {id:2,name:"Cajvana"},
    {id:3,name:"Campulung Moldovenesc"},
    {id:4,name:"Dolhasca"},
    {id:5,name:"Falticeni"},
    {id:6,name:"Frasin"},
    {id:7,name:"Gura Humorului"},
    {id:8,name:"Liteni"},
    {id:9,name:"Milisauti"},
    {id:10,name:"Radauti"},
    {id:11,name:"Salcea"},
    {id:12,name:"Siret"},
    {id:13,name:"Solca"},
    {id:14,name:"Suceava"},
    {id:15,name:"Vatra Dornei"},
    {id:16,name:"Vicovu de Sus"}
  ]},
{id:37,name:"Teleorman",city:[
    {id:1,name:"Alexandria"},
    {id:2,name:"Turnu Magurele"},
    {id:3,name:"Rosiorii de Vede"},
    {id:4,name:"Zimnicea"},
    {id:5,name:"Videle"}
  ]},
{id:38,name:"Timis",city:[
    {id:1,name:"Buzias"},
    {id:2,name:"Ciacova"},
    {id:3,name:"Deta"},
    {id:4,name:"Faget"},
    {id:5,name:"Gataia"},
    {id:6,name:"Jimbolia"},
    {id:7,name:"Lugoj"},
    {id:8,name:"Recas"},
    {id:9,name:"Sannicolau Mare"},
    {id:10,name:"Timisoara"}
  ]},
{id:39,name:"Tulcea",city:[
    {id:1,name:"Babadag"},
    {id:2,name:"Isaccea"},
    {id:3,name:"Macin"},
    {id:4,name:"Sulina"},
    {id:5,name:"Tulcea"}
  ]},
{id:40,name:"Valcea",city:[
    {id:1,name:"Babeni"},
    {id:2,name:"Baile Govora"},
    {id:3,name:"Baile Olanesti"},
    {id:4,name:"Balcesti"},
    {id:5,name:"Berbesti"},
    {id:6,name:"Brezoi"},
    {id:7,name:"Calimanesti"},
    {id:8,name:"Dragasani"},
    {id:9,name:"Horezu"},
    {id:10,name:"Ocnele Mari"},
    {id:11,name:"Ramnicu Valcea"}
  ]},
{id:41,name:"Vaslui",city:[
    {id:1,name:"Barlad"},
    {id:2,name:"Husi"},
    {id:3,name:"Murgeni"},
    {id:4,name:"Negresti"},
    {id:5,name:"Vaslui"}
  ]},
{id:42,name:"Vrancea",city:[
    {id:1,name:"Adjud"},
    {id:2,name:"Focsani"},
    {id:3,name:"Marasesti"},
    {id:4,name:"Odobesti"},
    {id:5,name:"Panciu"}
  ]}
]


export const category = [
  {id:1,name:"Constructii, Reparatii, Instalatii", subcategory:[
    {id:1,name:"Designer"},
    {id:2,name:"Electrician"},
    {id:3,name:"Instalator"},
    {id:4,name:"Mobilier si mobila pe comanda"},
    {id:5,name:"Tamplar"},
    {id:6,name:"Zidar"}
  ]},
  {id:2,name:"Educatie", subcategory:[
    {id:1,name:"Biologie"},
    {id:2,name:"Chimie"},
    {id:3,name:"Fizica"},
    {id:4,name:"Geografie"},
    {id:5,name:"Istorie"},
    {id:6,name:"Limbi straine"},
    {id:7,name:"Matematica"},
    {id:8,name:"Romana"}
  ]},
  {id:3,name:"Evenimente", subcategory:[
    {id:1,name:"Animator petreceri"},
    {id:2,name:"Aranjamente florale"},
    {id:3,name:"Cadouri lucrate manual"},
    {id:4,name:"Dansator"},
    {id:5,name:"Decoratiuni personalizate"},
    {id:6,name:"Solist"}
  ]},
  {id:4,name:"Igiena si frumusete", subcategory:[
    {id:1,name:"Coafor"},
    {id:2,name:"Cosmetica"},
    {id:3,name:"Frizerie"},
    {id:4,name:"Manichiura"},
    {id:5,name:"Masaj"}
  ]},
  {id:5,name:"Sanatate si asistenta sociala", subcategory:[
    {id:1,name:"Asistent/Ingrijitor medical"},
    {id:2,name:"Medicina de familie"},
    {id:3,name:"Medicina dentara"},
    {id:4,name:"Medicina specializata"},
    {id:5,name:"Medicina veterinara"},
    {id:6,name:"Mentenanta aparatura medicala"}
  ]},
  {id:6,name:"Servicii Juridice si Financiare", subcategory:[
    {id:1,name:"Avocat drept comercial-societar"},
    {id:2,name:"Avocat drept fiscal"},
    {id:3,name:"Avocat drept financiar-bancar"},
    {id:4,name:"Avocat drept penal"},
    {id:5,name:"Avocat dreptul familiei"},
    {id:6,name:"Avocat dreptul muncii"},
    {id:7,name:"Avocat insolventa si faliment"},
    {id:8,name:"Consultanta juridica"},
    {id:9,name:"Contabil"}
  ]},
  {id:7,name:"Servicii securitate", subcategory:[
    {id:1,name:"Securitate casa"},
    {id:2,name:"Securitate personala"}
  ]},
  {id:8,name:"Sport", subcategory:[
    {id:1,name:"Baschet"},
    {id:2,name:"Dans"},
    {id:3,name:"Gimnastica"},
    {id:4,name:"Handbal"},
    {id:5,name:"Innot"},
    {id:6,name:"Patinaj artistic"},
    {id:7,name:"Scrima"},
    {id:8,name:"Tenis"},
    {id:9,name:"Volei"}
  ]}
]

