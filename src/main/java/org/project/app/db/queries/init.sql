create table Patients(
            id UUID PRIMARY KEY,
            name varchar(50) NOT NULL,
            dob char(10) NOT NULL,
            sex char(1) NOT NULL,
            dc char(10) NOT NULL,
            du char(10) NOT NULL
           );
create table HealthProblems (
            diseaseName varchar(50) NOT NULL,
            degree int NOT NULL,
            ownerId UUID NOT NULL,
            FOREIGN KEY (ownerId) REFERENCES Patients
            );
insert into Patients(id, name, dob, sex, dc, du)
            values (
			'fd8771d2-4ebe-43cc-b029-287aaf170e83',
			'David',
			'12.03.2004',
			'M',
			'18.03.2022','18.03.2022'
			);

insert into HealthProblems(diseaseName, degree, ownerId)
            values (
		    'Stroke',
	        1,
 		    'fd8771d2-4ebe-43cc-b029-287aaf170e83'
            );