CREATE TABLE jtw_restaurant (
    restaurant_id   INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the restaurant',
    restaurant_name VARCHAR(50) NOT NULL COMMENT 'The name of the restaurant',
    meal_service    VARCHAR(50) NOT NULL COMMENT 'The meal services provided',
    start_time      TIME NOT NULL COMMENT 'The time the restaurant starts serving',
    end_time        TIME NOT NULL COMMENT 'The time the restaurant ends serving',
    floor           VARCHAR(20) NOT NULL COMMENT 'The floor location of the restaurant',
    PRIMARY KEY (restaurant_id)
);

CREATE TABLE jtw_entertainment (
    entertainment_id    INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the entertainment or activity',
    entertainment_name  VARCHAR(50) NOT NULL COMMENT 'The name of the entertainment or activity',
    number_of_unit      INT(2) NOT NULL COMMENT 'The number of units of this entertainment or activity',
    floor               VARCHAR(20) NOT NULL COMMENT 'The floor location of the entertainment',
    PRIMARY KEY (entertainment_id)
);

CREATE TABLE jtw_package (
    package_id       INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the package',
    package_name     VARCHAR(30) NOT NULL COMMENT 'The name of the package',
    price_per_person DECIMAL(7, 2) NOT NULL COMMENT 'The price of the service per person in USD',
    charge_period    VARCHAR(10) NOT NULL COMMENT 'The type could be per night or per trip',
    PRIMARY KEY (package_id)
);

CREATE TABLE jtw_stateroom (
    room_id         INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the stateroom',
    room_type       VARCHAR(30) NOT NULL COMMENT 'The type of the room, such as suite or balcony',
    room_size       DECIMAL(7, 2) NOT NULL COMMENT 'The size of the room in SQFT',
    bed_number      INT(1) NOT NULL COMMENT 'The number of beds in the room',
    bathroom_number FLOAT NOT NULL COMMENT 'The number of bathrooms in the stateroom',
    balcony_number  INT(1) NOT NULL COMMENT 'The number of balconies in the room',
    price           DECIMAL(7, 2) NOT NULL COMMENT 'The current price for each room and each location',
    location        VARCHAR(20) NOT NULL COMMENT 'The location of the room, such as Bow or Stern',
    PRIMARY KEY (room_id)
);

CREATE TABLE jtw_port (
    port_id         INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the port',
    port_name       VARCHAR(30) NOT NULL COMMENT 'The name of the port',
    port_state      VARCHAR(30) NOT NULL COMMENT 'The state of the port',
    port_nation     VARCHAR(30) NOT NULL COMMENT 'The nation where the port is',
    port_address    VARCHAR(255) NOT NULL COMMENT 'The detailed address of the port',
    nearest_airport VARCHAR(30) NOT NULL COMMENT 'The nearest airport of the port',
    parking_spot    INT(5) NOT NULL COMMENT 'The maximum parking spot number of the port',
    PRIMARY KEY (port_id)
);

CREATE TABLE jtw_trip (
    trip_id         INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of this trip',
    start_date_time DATETIME NOT NULL COMMENT 'The start time and date',
    end_date_time   DATETIME NOT NULL COMMENT 'The end date and time',
    total_night     INT NOT NULL COMMENT 'The total number of nights',
    start_port_id   INT NOT NULL,
    end_port_id     INT NOT NULL,
    FOREIGN KEY (start_port_id) REFERENCES jtw_port(port_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (end_port_id) REFERENCES jtw_port(port_id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (trip_id)
);

CREATE TABLE jtw_group (
    group_id         INT(5) NOT NULL AUTO_INCREMENT COMMENT 'The ID of the group',
    group_name       VARCHAR(30) NULL COMMENT 'The name of the group',
    passenger_number INT(4) NULL COMMENT 'The number of members in this group',
    trip_id          INT NULL,
    PRIMARY KEY (group_id),
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_passenger (
    passenger_id   INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of a passenger',
    name           VARCHAR(30) NOT NULL COMMENT 'The name of the passenger',
    date_of_birth  DATE NOT NULL COMMENT 'The date of birth of the passenger',
    gender         VARCHAR(10) NOT NULL COMMENT 'The gender of the passenger',
    phone_number   VARCHAR(13) NOT NULL COMMENT 'The phone number of the passenger',
    nationality    VARCHAR(20) NOT NULL COMMENT 'The nationality of the passenger',
    email          VARCHAR(30) NOT NULL COMMENT 'The email address of the passenger',
    addr_nation    VARCHAR(30) NOT NULL COMMENT 'The nation of the address',
    addr_state     VARCHAR(30) NOT NULL COMMENT 'The state of the address',
    addr_city      VARCHAR(30) NOT NULL COMMENT 'The city of the address',
    addr_street    VARCHAR(30) NOT NULL COMMENT 'The street of the address',
    addr_zipcode   VARCHAR(30) NOT NULL COMMENT 'The zipcode of the address',
    group_id       INT NULL COMMENT 'The group ID the passenger belongs to',
    PRIMARY KEY (passenger_id),
    FOREIGN KEY (group_id) REFERENCES jtw_group(group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_stopover (
    stopover_id   INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the stopover record',
    trip_id       INT NOT NULL COMMENT 'The trip ID this stopover belongs to',
    port_id       INT NOT NULL COMMENT 'The ID of the port for this stopover',
    arrival_time  DATETIME NOT NULL COMMENT 'The arrival time at the port',
    departure_time DATETIME NOT NULL COMMENT 'The departure time from the port',
    notes         VARCHAR(255) COMMENT 'Additional information about this stopover',
    PRIMARY KEY (stopover_id),
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (port_id) REFERENCES jtw_port(port_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_trip_entertainment (
    trip_id         INT NOT NULL COMMENT 'The ID of the trip',
    entertainment_id   INT NOT NULL COMMENT 'The ID of the entertainment',
    quantity       INT NOT NULL DEFAULT 1 COMMENT 'The number of units',
    PRIMARY KEY (trip_id, entertainment_id),
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (entertainment_id) REFERENCES jtw_entertainment(entertainment_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_trip_restaurant (
    trip_id         INT NOT NULL COMMENT 'The ID of the trip',
    restaurant_id   INT NOT NULL COMMENT 'The ID of the restaurant',
    quantity       INT NOT NULL DEFAULT 1 COMMENT 'The number of units',
    PRIMARY KEY (trip_id, restaurant_id),
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES jtw_restaurant(restaurant_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_invoice (
    invoice_id       INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the invoice',
    charge_date      DATE NOT NULL COMMENT 'The date the charge was made',
    charge_amount    DECIMAL(10, 2) NOT NULL COMMENT 'The amount charged in the invoice',
    remaining_amount DECIMAL(10, 2) NOT NULL DEFAULT 0 COMMENT 'The remaining amount yet to be paid in the invoice',
    trip_id          INT NOT NULL COMMENT 'The ID of the trip associated with this invoice',
    group_id         INT NOT NULL COMMENT 'The ID of the group associated with this invoice',
    PRIMARY KEY (invoice_id),
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES jtw_group(group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_payment (
    payment_id     INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of payment made by group',
    payment_amount DECIMAL(9, 2) NOT NULL COMMENT 'The payment made by group in USD',
    payment_time   DATETIME NOT NULL COMMENT 'The time the payment has been made',
    payment_method VARCHAR(30) NOT NULL COMMENT 'The method of payment',
    invoice_id     INT NOT NULL COMMENT 'The ID of the invoice',
    trip_id        INT NOT NULL COMMENT 'The ID of the trip associated with this charge',
    group_id       INT NOT NULL COMMENT 'The ID of the group associated with this charge',
    PRIMARY KEY (payment_id),
    FOREIGN KEY (invoice_id) REFERENCES jtw_invoice(invoice_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES jtw_group(group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_package_charge (
    package_charge_id INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the package charge',
    charge_date       DATE NOT NULL COMMENT 'The date of the charge',
    package_id        INT NOT NULL COMMENT 'The ID of the package associated with this charge',
    trip_id           INT NOT NULL COMMENT 'The ID of the trip associated with this charge',
    group_id          INT NOT NULL COMMENT 'The ID of the group associated with this charge',
    PRIMARY KEY (package_charge_id),
    FOREIGN KEY (package_id) REFERENCES jtw_package(package_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES jtw_group(group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_room_charge (
    room_charge_id   INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the room charge',
    charge_date      DATE NOT NULL COMMENT 'The date of the charge',
    room_id          INT NOT NULL COMMENT 'The ID of the room associated with this charge',
    trip_id           INT NOT NULL COMMENT 'The ID of the trip associated with this charge',
    group_id         INT NOT NULL COMMENT 'The ID of the group associated with this charge',
    PRIMARY KEY (room_charge_id),
    FOREIGN KEY (room_id) REFERENCES jtw_stateroom(room_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (group_id) REFERENCES jtw_group(group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE jtw_room_statistics (
    stat_id         INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the room statistics record',
    room_type       VARCHAR(50) NOT NULL COMMENT 'The type of the room (e.g., Suite, Balcony)',
    location        VARCHAR(20) NOT NULL COMMENT 'The location of the room (e.g., Bow, Stern)',
    price           DECIMAL(10, 2) NOT NULL COMMENT 'The price of the room',
    remaining_units INT NOT NULL DEFAULT 0 COMMENT 'The number of remaining units for this room type and location',
    trip_id         INT NOT NULL COMMENT 'The trip ID associated with this room statistic',
    PRIMARY KEY (stat_id),
    FOREIGN KEY (trip_id) REFERENCES jtw_trip(trip_id) ON DELETE CASCADE ON UPDATE CASCADE
) COMMENT 'Room statistics table to track available units by type and location';

CREATE TABLE jtw_user (
    user_id      INT NOT NULL AUTO_INCREMENT COMMENT 'The ID of the user account',
    username     VARCHAR(50) NOT NULL COMMENT 'The username for login',
    password     VARCHAR(255) NOT NULL COMMENT 'The encrypted password',
	role 	     VARCHAR(20) NOT NULL COMMENT 'User Role',
    group_id     INT NULL COMMENT 'The group ID associated with the user',
    PRIMARY KEY (user_id),
    FOREIGN KEY (group_id) REFERENCES jtw_group(group_id) ON DELETE CASCADE ON UPDATE CASCADE
);

   