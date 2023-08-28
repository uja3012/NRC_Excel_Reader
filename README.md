# NRC Excel Reader
This Command line application written in Java & build with maven to 
read data from only .xlsx file with multiple sheets and send data to apis.

#### API Request body accepts a payload of an array of:
    {
    name: string;
    date_of_birth: ISO-8601 string;
    address: string;
    phone_number: string;
    }
#### This CLI Application repo comes with 
    1. Source Code,
    2. executable jar,
    3. sample Windows batch file,
    4. sample ExcelSheet file 
    5. application properties.
please check released_cli folder for more details.

Command Line Application expects JRE & System property parameter (location of properties file) in command 
as shown below when executing from CLI
#### java -Dpath.properties=fullyQualifiedPathOfPropertyFile -jar jarfileName

application.properties file is important to update before executing batch/jar file

#### application.properties looks like below :
#### 
    #Mon Aug 28 17:37:58 SEPT 2023
    #api.URI=https://test-api.not.nrc.no/project-participants
    #api.NRC-API-KEY=your-nrcapi-key
    #xlsx.location=C:\\NRC_Excel_Reader\\src\\main\\resources\\project_participants.xlsx


#### Note : 
    Currently this utility is only supporting to specific format of .xlsx sheet 
    which is provided in the source code resources folder. We can work on this to 
    make ot more user friendly but needs time & efforts.

##### Example of Sheet Data mapped
    {
    "name": "Sophie Martin",
    "date_of_birth": "2022-11-03",
    "address": "789 Calle del Mar, Barcelona, Spain 08001",
    "phone_number": "+61 2 8765 4321"
    },
    {
    "name": "נועה כהן",
    "date_of_birth": "2023-04-09",
    "address": "543 Nørrebrogade, Copenhagen, Denmark 2200",
    "phone_number": "+86 10 1234 5678"
    },
    {
    "name": "राजेश शर्मा",
    "date_of_birth": "2022-10-16",
    "address": "210 Calea Victoriei, Bucharest, Romania 010063",
    "phone_number": "+7 495 123-45-67"
    },
    {
    "name": "สุชาดา วงศ์สวัสดิ์",
    "date_of_birth": "2023-01-30",
    "address": "765 Passeig de Gràcia, Barcelona, Spain 08008",
    "phone_number": "+971 4 234 5678"
    },
