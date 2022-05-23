package com.example.tara.Host;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.tara.Main.Main;
import com.example.tara.Models.CarHost;
import com.example.tara.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import android.os.Bundle;

public class EditHostCar extends AppCompatActivity implements View.OnClickListener {



        private ImageView ivCarGrant1,ivCarGrant2,ivCarGrant3,ivCarGrant4,
                ivRoadTax1,ivRoadTax2,ivRoadTax3,ivRoadTax4;
        AutoCompleteTextView etYear, etBrand, etTransmission, etDrivetrain,
                etSeats, etType, etFuelType, etMileage;
        private ImageView ivExterior1, ivExterior2,ivExterior3,ivExterior4,
                ivInterior1,ivInterior2,ivInterior3,ivInterior4;
        private EditText etModel, etPlateNumber;
        private CardView cvGrant1,cvGrant2,cvGrant3,cvTax1,cvTax2,cvTax3,cvExterior1,cvExterior2,cvExterior3,
                cvInterior1,cvInterior2,cvInterior3;
        private String yearValue,brandValue,transmissionValue,drivetrainValue,seatsValue,typeValue,fuelTypeValue,
                mileageValue;
        Uri exterior1Uri,exterior2Uri,exterior3Uri,exterior4Uri,interior1Uri,interior2Uri,interior3Uri,interior4Uri
                ,insurance1Uri,insurance2Uri,insurance3Uri,carGrant1Uri,carGrant2Uri,carGrant3Uri,carGrant4Uri
                ,roadTax1Uri,roadTax2Uri,roadTax3Uri,roadTax4Uri;
        EditText etPriceRate, etDescription;
        EditText etAmount;
        CardView cvInsurance1, cvInsurance2;
        ImageView ivInsurance1,ivInsurance2,ivInsurance3;
        private EditText etStreetName, etBrngy, etPostcode;
        private AutoCompleteTextView  etCity, etProvince;

        //all values, you can also put this on the string.xml para malinis tingnan
        String[] yearArr =  {"2022","2021","2020","2019","2018","2017","2016","2015","2014","2013","2012","2011"};
        String[] brandArr = {"Toyota","Mitsubishi","Nisan","Hyundai","Ford","Suzuki","Honda"};
        String[] transmissionArr = {"Manual","Automatic","CVT"};
        String[] drivetrainArr = {"AWD","4WD","FWD","RWD"};
        String[] seatArr = {"2 Seater","3 Seater","4 Seater","5 Seater","6 Seater"};
        String[] typeArr = {"Sedan","Coupe","Sport car","Station wagon","Hatchback","Convertible","SUV","Minivan"};
        String[] fuelTypeArr = {"Kerosene","Solar Oil","Diesel Oil","Fuel Oil","Biodiesel","Gasoline"};
        String[] mileageArr = {"50-100K km","100-150K km","150-200K km","200-250K km","250-300K km"};

        String[] cityArr = {"Alaminos","Angeles City","Antipolo","Bacolod","Bacoor","Bago","Baguio","Bais","Balanga",
                "Batac","Batangas City","Bayawan","Baybay","Bayugan","Biñan","Bislig","Bogo","Borongan",
                "Butuan","Cabadbaran","Cabanatuan","Cabuyao","Cadiz","Cagayan de Oro","Calamba","Calbayog",
                "Caloocan","Candon","Canlaon","Carcar","Catbalogan","Cauayan","Cavity City","Cebu City",
                "Cotabato City","Dagupan","Danao","Dapitan","Dasamariñas","Davao City","Digos","Dipolog",
                "Dumaguete","El Salvador","Escalante","Gapan","General Santos","General Trias","Gingoog",
                "Guihulngan","Himamaylan","Ilagan","Iligan","Iloilo City","Imus","Iriga","Isabela",
                "Kabankalan","Kidapawan","Korondal","La Carlota","Lamitan","Laoag","Lapu-lapu City",
                "Las Piñas","Legazpi","Ligao","Lipa","Lucena","Maasin","Mabalacat","Makati","Malabon",
                "Malaybalay","Malolos","Mandaluyong","Mandaue","Manila","Marawi","Marikina","Masbate City",
                "Mati","Meycauayan","Muñoz","Muntinlupa","Naga Camarines","Naga Cebu","Navotas","Olongapo",
                "Ormoc","Oroquieta","Ozamiz","Pagadian","Palayan","Panabo","Parañaque","Pasay","Pasig",
                "Passi","Puerto Princesa","Quezon City","Roxas","Sagay","Samal","San Carlos(Negros Occidental)",
                "San Carlos(Pangasinan)","San Fernando(La Union)","San Fernando(Pampanga)","San Jose",
                "San Jose del Monte","San Juan","San Pablo","San Pedro","Santa Rosa","Santo Tomas","Santiago",
                "Silay","Sipalay","Sorsogon City","Surigao City","Tabaco","Tabuk","Tacloban","Tacurong","Tagaytay",
                "Tagbilaran","Taguig","Tagum","Talisay(Cebu)","Talisay(Negros Occidental","Tanauan","Tandag",
                "Tangub","Tanjay","Tarlac City","Tayabas","Toledo","Trece Martires","Tuguegarao","Urdaneta","Valencia",
                "Valenzuela","Victorias","Vigan","Zamboanga City"};

        String[] provinceArr = {"Abra","Agusan Del Norte","Agusan Del Sur","Aklan","Albay","Antique","Apayao","Aurora","Basilan",
                "Bataan","Batanes","Batangas","Benguet","Biliran","Bohol","Bukidnon","Bulacan","Cagayan","Camarines Norte",
                "Camarines Sur","Camiguin","Capiz","Catanduanes","Cavite","Cebu","Compostella Valley","Cotabato","Davao Del Norte",
                "Davao Del Sur","Davao Occidental","Davao Oriental","Dinagat Islands","Eastern Samar","Guimaras","Ifugao",
                "Ilocos Norte","Ilocos Sur","Iloilo","Isabela","Kalinga","La Union","Laguna","Lanao Del Norte","Lanao Del Sur",
                "Leyte","Maguindanao","Marinduque","Masbate","Misamis Occidental","Misamis Oriental","Mountain Province",
                "Negros Occidental","Negros Oriental","Northern Samar","Nueva Ecija","Nueva Vizcaya","Occidental Mindoro",
                "Oriental Mindoro","Palawan","Pampanga","Pangasinan","Quezon","Quirino","Rizal","Romblon","Samar","Sarangani",
                "Siquijor","Sorsogon","South Cotabato","Southern Leyte","Sultan Kudarat","Sulu","Surigao Del Norte","Surigao Del Sur",
                "Tarlac","Tawi-tawi","Zambales","Zamboanga Del Norte","Zamboanga Del Sur","Zamboanga Sibugay"};

        String[] municipalityArr = {"Bangued","Boliney","Bucay","Bucloc","Daguioman","Danglas","Dolores","Lacub","Langangilang","Langiden","La Paz","Licuan=Baay",
                "Luba","Malibcong","Manabo","Peñarrubia","Pidigan","Pilar","Sallapadan","San Isidro","San Juan","San Quintin","Tayum","Tineg","Tubo","Villaviciosa",
                "Bacacay","Camalig","Daraga (Locsin)","Guinobatan","Jovellar","Libon","Malilipot","Malinao","Manito","Oas","Pio Duran","Polangui","Rapu-Rapu",
                "Santo Domingo (Libog)","Tiwi","Calanasan (Bayag)","Conner","Flora","Kabugao","Luna","Pudtol","Santa Marcela","Baler","Casiguran","Dilasag","Dinalungan","Dingalan",
                "Dipaculao","Maria Aurora","Maria Aurora","Abucay","Bagac","Dinalupihan","Hermosa","Limay","Mariveles","Morong","Orani","Orion","Pilar","Samal",
                "Basco","Itbayat","Ivana","Mahatao","Sabtang","Uyugan","Agoncillo","Alitagtag","Balayan","Balete","Bauan","Calaca","Calatagan","Cuenca",
                "Ibaan","Laurel","Lemery","Lian","Lobo","Mabini","Malvar","Mataasnakahoy","Nasugbu","Padre Garcia","Rosario","San Jose","San Juan","San Luis",
                "San Nicolas","San Pascual","Santa Teresita","Santo Tomas","Taal","Talisay","Taysan","Tingloy","Tuy","Atok","Bakun","Bokod","Buguias",
                "Itogon","Kabayan","Kapangan","Kibungan","La Trinidad","Mankayan","Sablan","Tuba","Tublay","Angat","Balagtas (Bigaa)","Baliuag","Bocaue","Bulacan (Bulakan)","Bustos",
                "Calumpit","Doña Remedios Trinidad","Guiguinto","Hagonoy","Marilao","Norzagaray","Obando","Pandi","Paombong","Plaridel","Pulilan","San Ildefonso",
                "San Miguel","San Rafael","Santa Maria","Abulug","Alcala","Allacapan","Pasil","Pinukpuk","Rizal (Liwan)","Tabuk","Tanudan","Tinglayan","Alaminos","Bay","Biñan",
                "Cabuyao","Calamba","Calauan","Cavinti","Famy","Kalayaan","Liliw","Los Baños","Luisiana","Lumban","Mabitac","Magdalena","Majayjay","Nagcarlan","Paete",
                "Pagsanjan","Pakil","Pangil","Pila","Rizal","San Pablo","San Pedro","Santa Cruz","Santa Maria","Santa Rosa","Siniloan","Victoria","Agoo","Aringay","Bacnotan",
                "Bagulin","Balaoan","Bangar","Bauang","Burgos","Caba","Luna","Naguilian","Pugo","Rosario","San Gabriel","San Juan","Santol","Santo Tomas",
                "Sudipen","Tubao","Boac","Buenavista","Gasan","Mogpog","Santa Cruz","Torrijos","Aroroy","Baleno","Balud","Batuan","Cataingan","Cawayan","Claveria",
                "Dimasalang","Esperanza","Mandaon","Milagros","Mobo","Monreal","Palanas","Pio V. Corpuz (Limbuhan)","Placer","San Fernando","San Jacinto","San Pascual","Uson","Barlig",
                "Bauko","Besao","Bontoc","Natonin","Paracelis","Sabangan","Sadanga","Sagada","Tadian","Pateros","Aliaga","Bongabon","Cabiao","Carranglan","Cuyapo",
                "Gabaldon (Bitulok - Sabani)","General Mamerto Natividad","General Tinio (Papaya)","Guimba","Jaen","Laur","Licab","Llanera","Lupao","Nampicuan","Pantabangan","Peñaranda","Quezon","Rizal","San Antonio",
                "San Isidro","San Leonardo","Santa Rosa","Santo Domingo","Talavera","Talugtug","Zaragoza","Alfonso Castañeda","Ambaguio","Aritao","Bagabag","Bambang","Bayombong","Diadi","Dupax del Norte",
                "Dupax del Sur","Kasibu","Kayapa","Quezon","Santa Fe","Solano","Villaverde","Abra de Ilog","Calintaan","Looc","Lubang","Magsaysay","Mamburao","Paluan","Rizal",
                "Sablayan","San Jose","Santa Cruz","Baco","Bongabong","Bulalacao (San Pedro)","Gloria","Mansalay","Naujan","Pinamalayan","Pola","Puerto Galera","Roxas","San Teodoro","Socorro",
                "Victoria","Aborlan","Agutaya","Araceli","Balabac","Bataraza","Brooke's Point","Busuanga","Cagayancillo","Coron","Culion","Cuyo","Dumaran","El Nido (Bacuit)","Kalayaan [Spratly Islands]",
                "Linapacan","Magsaysay","Narra","Quezon","Rizal (Marcos)","Roxas","San Vicente","Sofronio Española","Taytay","Apalit","Arayat","Bacolor","Candaba","Floridablanca","Guagua",
                "Lubao","Macabebe","Magalang","Masantol","Mexico","Minalin","Porac","San Luis","San Simon","Santa Ana","Santa Rita","Santo Tomas","Sasmuan (Sexmoan)","Agno","Aguilar",
                "Alcala","Anda","Asingan","Balungao","Bani","Basista","Bautista","Bayambang","Binalonan","Binmaley","Bolinao","Bugallon","Burgos","Calasiao","Dasol",
                "Infanta","Labrador","Laoac","Lingayen","Mabini","Malasiqui","Manaoag","Mangaldan","Mangatarem","Mapandan","Natividad","Pozzorrubio","Rosales","San Fabian","San Jacinto",
                "San Manuel","San Nicolas","San Quintin","Santa Barbara","Santa Maria","Santo Tomas","Sison","Sual","Tayug","Umingan","Urbiztondo","Villasis","Agdangan","Alabat","Atimonan",
                "Buenavista","Burdeos","Calauag","Candelaria","Catanauan","Dolores","General Luna","General Nakar","Guinayangan","Gumaca","Infanta","Jomalig","Lopez","Lucban","Macalelon",
                "Mauban","Mulanay","Padre Burgos","Pagbilao","Panukulan","Patnanungan","Perez","Pitogo","Plaridel","Polillo","Quezon","Real","Sampaloc","San Andres (Calolbon)","San Antonio",
                "San Francisco (Aurora)","San Narciso","Sariaya","Tagkawayan","Tiaong","Unisan","Aglipay","Cabarroguis","Diffun","Maddela","Nagtipunan","Saguday","Angono","Baras","Binangonan",
                "Cainta","Cardona","Jala-Jala","Morong","Pililla","Rodriguez (Montalban)","San Mateo","Tanay","Taytay","Teresa","Alcantara","Banton","Cajidiocan","Calatrava","Concepcion",
                "Corcuera","Ferrol","Looc","Magdiwang","Odiongan","Romblon","San Agustin","San Andres (Calolbon)","San Fernando","San Jose","Santa Fe","Santa Maria (Imelda)","Barcelona","Bulan","Bulusan",
                "Casiguran","Castilla","Donsol","Gubat","Irosin","Juban","Magallanes","Matnog","Pilar","Prieto Diaz","Santa Magdalena","Anao","Bamban","Camiling","Capas",
                "Concepcion","Gerona","La Paz","Mayantoc","Moncada","Paniqui","Pura","Ramos","San Clemente","San Jose","San Manuel","Santa Ignacia","Victoria","Botolan","Cabangan",
                "Candelaria","Castillejos","Iba","Masinloc","Palauig","San Antonio","San Felipe","San Marcelino","San Narciso","Santa Cruz","Subic"};

        ArrayAdapter<String> yearItems, brandItems, transmissionItems, driveItems, seatItems, typeItems,
                fuelItems,mileageItems;

        ArrayList<Uri> imageUri = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_host_car);
            getSupportActionBar().hide();

            Toolbar toolbar = findViewById(R.id.appBar);
            Button nextBtn = findViewById(R.id.aboutEditNextBtn);
            ivCarGrant1 = findViewById(R.id.carEditGrant1);
            ivCarGrant2 = findViewById(R.id.carEditGrant2);
            ivCarGrant3 = findViewById(R.id.carEditGrant3);
            ivCarGrant4 = findViewById(R.id.carEditGrant4);
            ivRoadTax1 = findViewById(R.id.roadEditTax1);
            ivRoadTax2 = findViewById(R.id.roadEditTax2);
            ivRoadTax3 = findViewById(R.id.roadEditTax3);
            ivRoadTax4 = findViewById(R.id.roadEditTax4);

            etYear = findViewById(R.id.etEditYear);
            etBrand = findViewById(R.id.etEditBrand);
            etModel = findViewById(R.id.etEditModel);
            etTransmission = findViewById(R.id.etEditTransmission);
            etDrivetrain = findViewById(R.id.etEditDrivetrain);
            etSeats = findViewById(R.id.etEditSeats);
            etType = findViewById(R.id.etEditType);
            etFuelType = findViewById(R.id.etEditFuelType);
            etMileage = findViewById(R.id.etEditMileage);
            etPlateNumber = findViewById(R.id.etEditPlateNumber);

            ivInterior1 = findViewById(R.id.carEditInterior1);
            ivInterior2 = findViewById(R.id.carEditInterior2);
            ivInterior3 = findViewById(R.id.carEditInterior3);
            ivInterior4 = findViewById(R.id.carEditInterior4);
            ivExterior1 = findViewById(R.id.carEditExterior1);
            ivExterior2 = findViewById(R.id.carEditExterior2);
            ivExterior3 = findViewById(R.id.carEditExterior3);
            ivExterior4 = findViewById(R.id.carEditExterior4);

            cvGrant1 = findViewById(R.id.editcv1);
            cvGrant2 = findViewById(R.id.editcv2);
            cvGrant3 = findViewById(R.id.editcv3);

            cvTax1 = findViewById(R.id.editcv4);
            cvTax2 = findViewById(R.id.editcv5);
            cvTax3 = findViewById(R.id.editcv6);

            cvExterior1 = findViewById(R.id.cvEditExterior1);
            cvExterior2 = findViewById(R.id.cvEditExterior2);
            cvExterior3 = findViewById(R.id.cvEditExterior3);
            cvInterior1 = findViewById(R.id.cvEditInterior1);
            cvInterior2 = findViewById(R.id.cvEditInterior2);
            cvInterior3 = findViewById(R.id.cvEditInterior3);

            etPriceRate=  findViewById(R.id.etEditPricing);
            etDescription = findViewById(R.id.etEditDescription);

            yearItems = new ArrayAdapter<String>(this,R.layout.list_item, yearArr);
            brandItems = new ArrayAdapter<String>(this,R.layout.list_item, brandArr);
            transmissionItems = new ArrayAdapter<String>(this,R.layout.list_item, transmissionArr);
            driveItems = new ArrayAdapter<String>(this,R.layout.list_item, drivetrainArr);
            seatItems = new ArrayAdapter<String>(this,R.layout.list_item, seatArr);
            typeItems = new ArrayAdapter<String>(this,R.layout.list_item, typeArr);
            fuelItems = new ArrayAdapter<String>(this,R.layout.list_item, fuelTypeArr);
            mileageItems = new ArrayAdapter<String>(this,R.layout.list_item, mileageArr);

            etYear.setAdapter(yearItems);
            etBrand.setAdapter(brandItems);
            etTransmission.setAdapter(transmissionItems);
            etDrivetrain.setAdapter(driveItems);
            etSeats.setAdapter(seatItems);
            etType.setAdapter(typeItems);
            etFuelType.setAdapter(fuelItems);
            etMileage.setAdapter(mileageItems);

            etAmount= findViewById(R.id.etEditAmount);
            cvInsurance1 = findViewById(R.id.cvEditInsurance1);
            cvInsurance2 = findViewById(R.id.cvEditInsurance2);
            ivInsurance1 = findViewById(R.id.ivEditInsurance1);
            ivInsurance2 = findViewById(R.id.ivEditInsurance2);
            ivInsurance3 = findViewById(R.id.ivEditInsurance3);
            RadioGroup radioGroup = findViewById(R.id.insuranceEditRadioGroup);

            etStreetName = findViewById(R.id.etEditAddressLine1);
            etBrngy = findViewById(R.id.etEditAddressLine2);
            etCity = findViewById(R.id.etEditCity);
            etPostcode = findViewById(R.id.etEditPostcode);
            etProvince =findViewById(R.id.etEditProvince);

            RadioGroup protectionRadioGroup = findViewById(R.id.protectionEditRadioGroup);
            RadioGroup trackCarRadioGroup = findViewById(R.id.trackEditRadioGroup);

            ivCarGrant1.setOnClickListener(this);
            ivCarGrant2.setOnClickListener(this);
            ivCarGrant3.setOnClickListener(this);
            ivCarGrant4.setOnClickListener(this);
            ivRoadTax1.setOnClickListener(this);
            ivRoadTax2.setOnClickListener(this);
            ivRoadTax3.setOnClickListener(this);
            ivRoadTax4.setOnClickListener(this);
            ivExterior1.setOnClickListener(this);
            ivExterior2.setOnClickListener(this);
            ivExterior3.setOnClickListener(this);
            ivExterior4.setOnClickListener(this);
            ivInterior1.setOnClickListener(this);
            ivInterior2.setOnClickListener(this);
            ivInterior3.setOnClickListener(this);
            ivInterior4.setOnClickListener(this);
            ivInsurance1.setOnClickListener(this);
            ivInsurance2.setOnClickListener(this);
            ivInsurance3.setOnClickListener(this);

            ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,R.layout.list_item,cityArr);
            ArrayAdapter<String> provinceAdapter = new ArrayAdapter<>(this,R.layout.list_item,provinceArr);
            etCity.setAdapter(cityAdapter);
            etProvince.setAdapter(provinceAdapter);

            etYear.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    yearValue = parent.getItemAtPosition(position).toString();
                }
            });

            etBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    brandValue = parent.getItemAtPosition(position).toString();
                }
            });

            etTransmission.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    transmissionValue = parent.getItemAtPosition(position).toString();
                }
            });

            etDrivetrain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    drivetrainValue = parent.getItemAtPosition(position).toString();
                }
            });

            etSeats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    seatsValue = parent.getItemAtPosition(position).toString();
                }
            });

            etType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    typeValue = parent.getItemAtPosition(position).toString();
                }
            });

            etFuelType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    fuelTypeValue = parent.getItemAtPosition(position).toString();
                }
            });

            etMileage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mileageValue = parent.getItemAtPosition(position).toString();
                }
            });

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(yearValue==null||brandValue==null||transmissionValue==null||drivetrainValue==null||seatsValue==null
                            ||typeValue==null||fuelTypeValue==null||mileageValue==null||etModel.getText().toString().equals("")||
                            etPlateNumber.getText().toString().equals("")){
                        Toast.makeText(EditHostCar.this,"Some of the fields are empty!",Toast.LENGTH_LONG).show();
                    }else {
                        uploadImage("carImages/");
                    }
                }
            });
        }

        //upload data to database, from the name itself
        private void uploadData(String carUrl){
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String databaseLocation = getString(R.string.databasePath);
            String address1 = etStreetName.getText().toString();
            String address2 = etBrngy.getText().toString();
            String city = etCity.getText().toString();
            String postcode = etPostcode.getText().toString();
            String province = etProvince.getText().toString();
            String year = etYear.getText().toString();
            String brand = etBrand.getText().toString();
            String transmission = etTransmission.getText().toString();
            String drivetrain = etDrivetrain.getText().toString();
            String seats = etSeats.getText().toString();
            String type = etType.getText().toString();
            String fuelType = etFuelType.getText().toString();
            String mileage = etMileage.getText().toString();
            String model = etModel.getText().toString();
            String plateNumber = etPlateNumber.getText().toString();
            String priceRate = etPriceRate.getText().toString();
            String description = etDescription.getText().toString();
            String bmy = brand + " " + model + " " + year;
            String location = address1 + " " + address2 +" " + city + " " + province;

            CarHost carHost = new CarHost(address1,address2,city,postcode,province,year,brand,transmission,
                    drivetrain,seats,type,fuelType,mileage,model,plateNumber,priceRate,description,carUrl, bmy, location);

            FirebaseDatabase.getInstance(databaseLocation).getReference().child("users").child(userId)
                    .child("isHost").setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(EditHostCar.this,"Something went wrong, try again",Toast.LENGTH_LONG).show();
                }
            });

            FirebaseDatabase.getInstance(databaseLocation).getReference().child("vehicle").push().child(userId)
                    .setValue(carHost).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent intent = new Intent(EditHostCar.this, Main.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        //opens image selector
        private void selectImage(int requestCode){
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, requestCode);
        }

        //upload image to storage
        private void uploadImage(String fileLocation){
            //filename format
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
            Date now = new Date();
            String fileName = formatter.format(now);

            //gets the location of the storage
            StorageReference storageReference = FirebaseStorage.getInstance("gs://tara-351111.appspot.com/").getReference(fileLocation+fileName);


            for(int i = 0; i < imageUri.size(); i++){
                storageReference.putFile(imageUri.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            // gets the image url and store in the realtime database
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String imageUrl = task.getResult().toString();
                                uploadData(imageUrl);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditHostCar.this, "Something occurred, please try again later",Toast.LENGTH_LONG).show();
                    }
                });
            }


            //stores the image
//        storageReference.putFile(exterior1Uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    // gets the image url and store in the realtime database
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        String imageUrl = task.getResult().toString();
//                        uploadData(imageUrl);
//                    }
//                });
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(HostCar.this, "Something occurred, please try again later",Toast.LENGTH_LONG).show();
//            }
//        });
        }

        //this method is executed when an image is selected
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            //if image is selected based on the code, display the image
            if(requestCode==1 && resultCode== -1 && data != null && data.getData() != null){
                carGrant1Uri = data.getData();
                ivCarGrant1.setImageURI(carGrant1Uri);
                cvGrant1.setVisibility(View.VISIBLE);
                imageUri.add(carGrant1Uri);
            }
            if(requestCode==2 && resultCode== -1 && data != null && data.getData() != null){
                carGrant2Uri = data.getData();
                ivCarGrant2.setImageURI(carGrant2Uri);
                cvGrant2.setVisibility(View.VISIBLE);
                imageUri.add(carGrant4Uri);
            }
            if(requestCode==3 && resultCode== -1 && data != null && data.getData() != null){
                carGrant3Uri = data.getData();
                ivCarGrant3.setImageURI(carGrant3Uri);
                cvGrant3.setVisibility(View.VISIBLE);
                imageUri.add(carGrant4Uri);
            }
            if(requestCode==4 && resultCode== -1 && data != null && data.getData() != null){
                carGrant4Uri = data.getData();
                ivCarGrant4.setImageURI(carGrant4Uri);
                imageUri.add(carGrant4Uri);
            }
            if(requestCode==5 && resultCode== -1 && data != null && data.getData() != null){
                roadTax1Uri = data.getData();
                ivRoadTax1.setImageURI(roadTax1Uri);
                cvTax1.setVisibility(View.VISIBLE);
                imageUri.add(roadTax1Uri);
            }
            if(requestCode==6 && resultCode== -1 && data != null && data.getData() != null){
                roadTax2Uri = data.getData();
                ivRoadTax2.setImageURI(roadTax2Uri);
                cvTax2.setVisibility(View.VISIBLE);
                imageUri.add(roadTax2Uri);
            }
            if(requestCode==7 && resultCode== -1 && data != null && data.getData() != null){
                roadTax3Uri = data.getData();
                ivRoadTax3.setImageURI(roadTax3Uri);
                cvTax3.setVisibility(View.VISIBLE);
                imageUri.add(roadTax3Uri);
            }
            if(requestCode==8 && resultCode== -1 && data != null && data.getData() != null){
                roadTax4Uri = data.getData();
                ivRoadTax4.setImageURI(roadTax4Uri);
                imageUri.add(roadTax4Uri);
            }
            if(requestCode==9 && resultCode== -1 && data != null && data.getData() != null){
                exterior1Uri = data.getData();
                ivExterior1.setImageURI(exterior1Uri);
                cvExterior1.setVisibility(View.VISIBLE);
                imageUri.add(exterior1Uri);
            }
            if(requestCode==10 && resultCode== -1 && data != null && data.getData() != null){
                exterior2Uri = data.getData();
                ivExterior2.setImageURI(exterior2Uri);
                cvExterior2.setVisibility(View.VISIBLE);
                imageUri.add(exterior2Uri);
            }
            if(requestCode==11 && resultCode== -1 && data != null && data.getData() != null){
                exterior3Uri = data.getData();
                ivExterior3.setImageURI(exterior3Uri);
                cvExterior3.setVisibility(View.VISIBLE);
                imageUri.add(exterior3Uri);
            }
            if(requestCode==12 && resultCode== -1 && data != null && data.getData() != null){
                exterior4Uri = data.getData();
                ivExterior4.setImageURI(exterior4Uri);
                imageUri.add(exterior4Uri);
            }
            if(requestCode==13 && resultCode== -1 && data != null && data.getData() != null){
                interior1Uri = data.getData();
                ivInterior1.setImageURI(interior1Uri);
                cvInterior1.setVisibility(View.VISIBLE);
                imageUri.add(interior1Uri);
            }
            if(requestCode==14 && resultCode== -1 && data != null && data.getData() != null){
                interior2Uri = data.getData();
                ivInterior2.setImageURI(interior2Uri);
                cvInterior2.setVisibility(View.VISIBLE);
                imageUri.add(interior2Uri);
            }
            if(requestCode==15 && resultCode== -1 && data != null && data.getData() != null){
                interior3Uri = data.getData();
                ivInterior3.setImageURI(interior3Uri);
                cvInterior3.setVisibility(View.VISIBLE);
                imageUri.add(interior3Uri);
            }
            if(requestCode==16 && resultCode== -1 && data != null && data.getData() != null){
                interior4Uri = data.getData();
                ivInterior4.setImageURI(interior4Uri);
                imageUri.add(interior4Uri);
            }
            if (requestCode == 17 && resultCode == -1 && data != null && data.getData() != null) {
                insurance1Uri = data.getData();
                ivInsurance1.setImageURI(insurance1Uri);
                cvInsurance1.setVisibility(View.VISIBLE);
                imageUri.add(insurance1Uri);
            }
            if (requestCode == 18 && resultCode == -1 && data != null && data.getData() != null) {
                insurance2Uri = data.getData();
                ivInsurance2.setImageURI(insurance2Uri);
                cvInsurance2.setVisibility(View.VISIBLE);
                imageUri.add(insurance2Uri);
            }
            if (requestCode == 19 && resultCode == -1 && data != null && data.getData() != null) {
                insurance3Uri = data.getData();
                ivInsurance3.setImageURI(interior3Uri);
                imageUri.add(insurance3Uri);
            }
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.carGrant1:
                    selectImage(1);
                    break;
                case R.id.carGrant2:
                    selectImage(2);
                    break;
                case R.id.carGrant3:
                    selectImage(3);
                    break;
                case R.id.carGrant4:
                    selectImage(4);
                    break;
                case R.id.roadTax1:
                    selectImage(5);
                    break;
                case R.id.roadTax2:
                    selectImage(6);
                    break;
                case R.id.roadTax3:
                    selectImage(7);
                    break;
                case R.id.roadTax4:
                    selectImage(8);
                    break;
                case R.id.carExterior1:
                    selectImage(9);
                    break;
                case R.id.carExterior2:
                    selectImage(10);
                    break;
                case R.id.carExterior3:
                    selectImage(11);
                    break;
                case R.id.carExterior4:
                    selectImage(12);
                    break;
                case R.id.carInterior1:
                    selectImage(13);
                    break;
                case R.id.carInterior2:
                    selectImage(14);
                    break;
                case R.id.carInterior3:
                    selectImage(15);
                    break;
                case R.id.carInterior4:
                    selectImage(16);
                    break;
                case R.id.ivInsurance1:
                    selectImage(17);
                    break;
                case R.id.ivInsurance2:
                    selectImage(18);
                    break;
                case R.id.ivInsurance3:
                    selectImage(19);
                    break;
            }
        }
    }
