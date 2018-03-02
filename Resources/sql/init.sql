begin transaction;

CREATE TYPE VehiculeType AS ENUM (
	'Motorcycle',
	'Car'
);

CREATE TYPE Sign AS ENUM (
	'=',
	'<',
	'>',
	'<=',
	'>=',
	'!='
);

CREATE TABLE MeterScale (
	id serial PRIMARY KEY,
	effectiveDate date NOT NULL UNIQUE,
	vehicleType VehiculeType NOT NULL
);

CREATE TABLE MetersCondition (
	id serial PRIMARY KEY,
	idMeterScale BIGINT NOT NULL,
	sign Sign NOT NULL,
	meters BIGINT NOT NULL,
	idFeesBases BIGINT NOT NULL,
	UNIQUE(idMeterScale, sign, meters)
);

ALTER TABLE MetersCondition ADD CONSTRAINT ScaleOfMetersCondition FOREIGN KEY (idMeterScale) REFERENCES MeterScale(id) ON DELETE CASCADE;

CREATE TABLE FeesBase (
	id serial PRIMARY KEY,
	idMeterScale BIGINT NOT NULL,
	fiscalHorsepower SMALLINT NOT NULL,
	multiplicationRate NUMERIC(5,3) NOT NULL,
	additionRate NUMERIC(10,3) NOT NULL,
	UNIQUE(fiscalHorsepower, idMeterScale)
);

ALTER TABLE MetersCondition ADD CONSTRAINT idMetersConditionOfInterval FOREIGN KEY (idFeesBases) REFERENCES FeesBase(id) ON DELETE CASCADE;

ALTER TABLE FeesBase ADD CONSTRAINT ScaleOfFeesBase FOREIGN KEY (idMeterScale) REFERENCES MeterScale(id) ON DELETE CASCADE;

CREATE TYPE PhoneType AS ENUM (
	'Home',
	'Work',
	'iPhone',
	'Mobile',
	'Personal'
);

CREATE TYPE Gender AS ENUM(
	'MALE',
	'FEMALE'
);

CREATE TABLE Address(
	id serial PRIMARY KEY,
	label text,
	streetNumber varchar(38),
	street varchar(38),
	zipCode int NOT NULL,
	city varchar(38) NOT NULL,
	country varchar(38) NOT NULL,
	state varchar(38),
	location varchar(100) NOT NULL,
	place_id varchar(100) NOT NULL,
	url text,
	formatted_address varchar(250) NOT NULL
);

CREATE TABLE Employee (
	id serial PRIMARY KEY,
	firstName varchar(20) NOT NULL,
	lastName varchar(20) NOT NULL,
	gender Gender NOT NULL,
	birthDate date NOT NULL,
	licenceNumber varchar(50),
	licenceDate date,
	mail varchar(50)
);


CREATE TABLE Enterprise (
	id serial PRIMARY KEY,
	label text NOT NULL,
	creationDate date NOT NULL,
	taxYear date,
	siretNumber varchar(50) NOT NULL
);

CREATE TABLE AddressEmployee(
	idEmployee BIGINT NOT NULL,
	idAddress BIGINT NOT NULL,
	PRIMARY KEY(idEmployee, idAddress)
);

ALTER TABLE AddressEmployee ADD CONSTRAINT AddressFKEmp FOREIGN KEY (idAddress) REFERENCES Address(id) ON DELETE CASCADE;
ALTER TABLE AddressEmployee ADD CONSTRAINT AddressOf FOREIGN KEY (idEmployee) REFERENCES Employee(id) ON DELETE CASCADE;

CREATE TABLE AddressEnterprise(
	idEnterprise BIGINT NOT NULL,
	idAddress BIGINT NOT NULL,
	PRIMARY KEY(idEnterprise, idAddress)
);

ALTER TABLE AddressEnterprise ADD CONSTRAINT AddressFKEnt FOREIGN KEY (idAddress) REFERENCES Address(id) ON DELETE CASCADE;
ALTER TABLE AddressEnterprise ADD CONSTRAINT AddressOf FOREIGN KEY (idEnterprise) REFERENCES Enterprise(id) ON DELETE CASCADE;

CREATE TABLE PhoneNumber(
	id serial PRIMARY KEY,
	phoneNumber varchar(12) NOT NULL,
	phoneType PhoneType NOT NULL,
	UNIQUE(phoneNumber, phoneType)
);

CREATE TABLE PhoneNumberEnterprise(
	idEnterprise BIGINT NOT NULL,
	idPhoneNumber BIGINT NOT NULL,
	PRIMARY KEY(idEnterprise, idPhoneNumber)
);

ALTER TABLE PhoneNumberEnterprise ADD CONSTRAINT NumberOfEnterprise FOREIGN KEY (idEnterprise) REFERENCES Enterprise(id) ON DELETE CASCADE;
ALTER TABLE PhoneNumberEnterprise ADD CONSTRAINT hisPhoneEnt FOREIGN KEY (idPhoneNumber) REFERENCES PhoneNumber(id) ON DELETE CASCADE;

CREATE TABLE PhoneNumberEmployee(
	idEmployee BIGINT NOT NULL,
	idPhoneNumber BIGINT NOT NULL,
	PRIMARY KEY(idEmployee, idPhoneNumber)
);

ALTER TABLE PhoneNumberEmployee ADD CONSTRAINT NumberOfEmployee FOREIGN KEY (idEmployee) REFERENCES Employee(id) ON DELETE CASCADE;
ALTER TABLE PhoneNumberEmployee ADD CONSTRAINT hisPhoneEmp FOREIGN KEY (idEmployee) REFERENCES PhoneNumber(id) ON DELETE CASCADE;

CREATE TABLE Car (
	id serial PRIMARY KEY,
	model varchar(50) NOT NULL,
	fiscalHorsepower SMALLINT NOT NULL,
	registrationDate date NOT NULL,
	idEmployee BIGINT NOT NULL
);

ALTER TABLE Car ADD CONSTRAINT carOwner FOREIGN KEY (idEmployee) REFERENCES Employee(id) ON DELETE CASCADE;


CREATE TYPE AttachmentType AS ENUM (
	'Photo',
	'CG',
	'License',
	'Other'
);

CREATE TABLE Attachment(
	id serial PRIMARY KEY,
	type AttachmentType NOT NULL,
	path text NOT NULL,
	name text NOT NULL,
	ext varchar(5) NOT NULL
);

CREATE TABLE AttachmentEmployee(
	idAttachment BIGINT NOT NULL,
	idEmployee BIGINT NOT NULL,
	PRIMARY KEY(idAttachment, idEmployee)
);

ALTER TABLE AttachmentEmployee ADD CONSTRAINT empAtt FOREIGN KEY (idAttachment) REFERENCES Attachment(id) ON DELETE CASCADE;
ALTER TABLE AttachmentEmployee ADD CONSTRAINT attOfEmp FOREIGN KEY (idEmployee) REFERENCES Employee(id) ON DELETE CASCADE;

CREATE TABLE AttachmentEnterprise(
	idAttachment BIGINT NOT NULL,
	idEnterprise BIGINT NOT NULL,
	PRIMARY KEY(idAttachment, idEnterprise)
);

ALTER TABLE AttachmentEnterprise ADD CONSTRAINT entAtt FOREIGN KEY (idAttachment) REFERENCES Attachment(id) ON DELETE CASCADE;
ALTER TABLE AttachmentEnterprise ADD CONSTRAINT attOfEnt FOREIGN KEY (idEnterprise) REFERENCES Enterprise(id) ON DELETE CASCADE;

CREATE TABLE AttachmentCar(
	idAttachment BIGINT NOT NULL,
	idCar BIGINT NOT NULL,
	PRIMARY KEY(idAttachment, idCar)
);

ALTER TABLE AttachmentCar ADD CONSTRAINT carAtt FOREIGN KEY (idAttachment) REFERENCES Attachment(id) ON DELETE CASCADE;
ALTER TABLE AttachmentCar ADD CONSTRAINT attOfCar FOREIGN KEY (idCar) REFERENCES Car(id) ON DELETE CASCADE;

CREATE TABLE AttachmentAddress(
	idAttachment BIGINT NOT NULL,
	idAddress BIGINT NOT NULL,
	PRIMARY KEY(idAttachment, idAddress)
);

ALTER TABLE AttachmentAddress ADD CONSTRAINT addrAtt FOREIGN KEY (idAttachment) REFERENCES Attachment(id) ON DELETE CASCADE;
ALTER TABLE AttachmentAddress ADD CONSTRAINT attOfAddr FOREIGN KEY (idAddress) REFERENCES Address(id) ON DELETE CASCADE;

CREATE TABLE EmployeeEnterpriseJoin(
	idEmployee BIGINT NOT NULL,
	idEnterprise BIGINT NOT NULL,
	PRIMARY KEY(idEmployee, idEnterprise)
);

ALTER TABLE EmployeeEnterpriseJoin ADD CONSTRAINT emp FOREIGN KEY (idEmployee) REFERENCES Employee(id) ON DELETE CASCADE;
ALTER TABLE EmployeeEnterpriseJoin ADD CONSTRAINT ent FOREIGN KEY (idEnterprise) REFERENCES Enterprise(id) ON DELETE CASCADE;

commit transaction;
