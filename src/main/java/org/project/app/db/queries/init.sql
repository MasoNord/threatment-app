create table Patients(
            id UUID PRIMARY KEY,
            name char(50) NOT NULL,
            dob char(11) NOT NULL,
            sex char(1) NOT NULL,
            dc char(11) NOT NULL,
            du char(11) NOT NULL
           );
create table HealthProblems (
            name char(50) NOT NULL,
            degree int NOT NULL,
            ownerId UUID NOT NULL,
            PRIMARY KEY(name),
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

insert into HealthProblems(name, degree, ownerId)
            values (
		    'Stroke',
	        1,
 		    'fd8771d2-4ebe-43cc-b029-287aaf170e83'
            );