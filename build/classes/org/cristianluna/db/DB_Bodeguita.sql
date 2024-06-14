-- drop database DB_Bodeguita;
Create database DB_Bodeguita;
use DB_Bodeguita;

create table Clientes(
	codigoCliente int not null,
	NITCliente varchar(10) not null,
	nombreCliente varchar(50)not null,
	apellidoCliente varchar(50) not null,	
	direccionCliente varchar(250),
	telefonoCliente varchar(8),
	correoCliente varchar(45),
	primary key PK_Clientes(codigoCliente)
);

create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar(60) not null,
    apellidoProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor(codigoProveedor)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
	nombreCargo varchar(45),
	descripcionCargo varchar(45),
	primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);

create table TipoProducto(
	codigoTipoProducto int not null,
	descripcion varchar(45),
	primary key PK_codigoTipoProducto(codigoTipoProducto)
);

create table Compras(
	numeroDocumento int not null,
	fechaDocumento date,
	descripcion varchar (60),
	totalDocumento decimal(10,2),
	primary key PK_numeroDocumento(numeroDocumento)
);

Create table EmailProveedor(
	codigoEmailProveedor int not null, 
    emailProveedor varchar (50),
    descripcion varchar (100),
    codigoProveedor int not null,
    primary key PK_codigoEmailProveedor(codigoEmailProveedor),
    constraint FK_Proveedores_codigoProveedorE foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

Create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar (8),
    numeroSecundario varchar (8),
    observaciones varchar (45),
    codigoProveedor int not null, 
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
	constraint FK_Proveedores_codigoProveedorT foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

Create table Productos(
	codigoProducto int not null,
    descripcion varchar (45),
    precioUnitario decimal (10, 2), 
    precioDocena decimal (10, 2),
	precioMayor decimal (10, 2), 
    imagenProducto varchar (45),
    existencia int, 
    codigoTipoProducto int not null, 
    codigoProveedor int not null,
    primary key PK_codigoProducto(codigoProducto), 
    constraint FK_TipoProducto_codigoTipoProductoP foreign key (codigoTipoProducto)
		references TipoProducto (codigoTipoProducto),
	constraint FK_Proveedores_codigoProveedorP foreign key (codigoProveedor)
		references Proveedores (codigoProveedor)
);

Create table DetalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal (10, 2),
    cantidad int, 
    codigoProducto int not null,
    numeroDocumento int not null, 
    primary key PK_codigoDetalleCompra(codigoDetalleCompra), 
    constraint FK_Productos_codigoProductoDC foreign key (codigoProducto)
		references Productos (codigoProducto),
	constraint FK_Compras_numeroDocumentoDC foreign key (numeroDocumento)
		references Compras (numeroDocumento)
);

Create table Empleados(
	codigoEmpleado int not null,
    nombresEmpleado varchar (50),
    apellidosEmpleado varchar (50),
    sueldo decimal (10, 2), 
    direccion varchar (150),
    turno varchar (15),
    codigoCargoEmpleado int not null,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint FK_CargoEmpleado_codigoCargoEmpleadoE foreign key (codigoCargoEmpleado)
		references CargoEmpleado (codigoCargoEmpleado)
);

Create table Factura(
	numeroFactura int not null,
    estado varchar (50),
    totalFactura decimal (10, 2),
    fechaFactura date,
    codigoCliente int,
    codigoEmpleado int,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_Clientes_codigoClienteF foreign key (codigoCliente)
		references Clientes (codigoCliente),
	constraint FK_Empleados_codigoEmpleadoF foreign key (codigoEmpleado)
		references Empleados (codigoEmpleado)
);

Create table DetalleFactura(
	codigoDetalleFactura int not null, 
    precioUnitario decimal (10, 2),
    cantidad int, 
    numeroFactura int,
    codigoProducto int, 
    primary key PK_codigoDetalleFactura(codigoDetalleFactura),
    constraint FK_Factura_numeroFacturaDF foreign key (numeroFactura)
		references Factura (numeroFactura),
	constraint FK_Productos_codigoProductoDF foreign key (codigoProducto)
		references Productos (codigoProducto)
);

-- -------------------- Procedimientos almacenados ---------------------
-- -------------------- Clientes --------------------------------------
-- -------------------- Agregar Clientes -----------------------------
Delimiter $$
	create procedure sp_agregarCliente (in codigoCliente int, NITcliente varchar(10), in nombreCliente varchar(50), in apellidoCliente varchar(50),
    in direccionCliente varchar(250), in telefonoCliente varchar(8), in correoCliente varchar(45))
		Begin 
			Insert into Clientes (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente) 
            values (codigoCliente, NITCliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente);
	end $$
Delimiter ;

call sp_AgregarCliente(01, '67565788', 'Cris', 'Luna', 'Zona 7', '56567878','cluna-2023308@kinal.edu.gt');
call sp_AgregarCliente(02, '77888659', 'Alexis', 'Donis', 'Zona 3', '41444122','adonis-2023565@kinal.edu.gt');

-- -----------------------------------------Listar Clientes -----------------------------------
Delimiter $$
	create procedure sp_ListarClientes()
		Begin
			select
            C.codigoCliente,
            C.NITCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		end $$
Delimiter ;

call sp_ListarClientes();
-- -----------------------------Buscar Cliebtes ---------------------------------------------
Delimiter $$
	create procedure sp_BuscarClientes (in codCliente int)
		Begin
			select
            C.codigoCliente,
            C.NITCliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C
            where codigoCliente = codCliente;
		end $$
Delimiter ;

call sp_BuscarClientes(01);

-- ------------------------------------Eliminar Clientes -------------------------------------------
Delimiter $$
	create procedure sp_EliminarClientes (in codCli int)
		Begin 
			Delete from Clientes
				where codigoCliente = codCli;
		End$$
Delimiter ;

call sp_EliminarClientes(02);

-- -----------------------------------------------Editar Clientes ------------------------------------------------------
Delimiter $$
	Create procedure sp_EditarCliente(in _codigoCliente int, _NITcliente varchar(10), in _nombreCliente varchar(50), in _apellidoCliente varchar(50),
    in _direccionCliente varchar(250), in _telefonoCliente varchar(8), in _correoCliente varchar(45))
    Begin
		update Clientes
			set
            Clientes.codigoCliente = _codigoCliente,
            Clientes.NITCliente = _NITcliente,
            Clientes.nombreCliente = _nombreCliente,
            Clientes.apellidoCliente = _apellidoCliente,
            Clientes.direccionCliente = _direccionCliente,
            Clientes.telefonoCliente = _telefonoCliente,
            Clientes.correoCliente = _correoCliente
            where Clientes.codigoCliente = _codigoCliente;
	end $$
Delimiter ;

call sp_EditarCliente(01, '78789898', 'Christian', 'Sisimit', 'Zona 2', '78789090','csisimit-2023309@kinal.edu.gt');
call sp_ListarClientes();

-- ------------------------------- Procedimientos Almacenados ---------------------------------------
-- ------------------------------- Proveedores ------------------------------------------------------
-- --------------------------------Agregar Proveedores ----------------------------------------------
Delimiter $$
	Create procedure sp_AgregarProveedores (in codigoProveedor int, in NITproveedor varchar(10), in nombreProveedor varchar(60),
    in apellidoProveedor varchar(60), in direccionProveedor varchar(150), in razonSocial varchar(60), in contactoPrincipal varchar(50), in paginaWeb varchar (50))
		Begin 
			Insert into Proveedores(codigoProveedor ,NITproveedor ,nombreProveedor ,apellidoProveedor ,direccionProveedor ,razonSocial ,contactoPrincipal,
            paginaWeb)
            values (codigoProveedor ,NITproveedor ,nombreProveedor ,apellidoProveedor ,direccionProveedor ,razonSocial ,contactoPrincipal,
            paginaWeb);
		end $$
Delimiter ;

call sp_AgregarProveedores(01,'9842749','Lionel','Messi','Zona 10','Vender','10101010','mundial2022.com');
call sp_AgregarProveedores(02,'3352200','Cristiano','Penaldo','Zona 7','Vender','07070707','segundon2.com');
call sp_AgregarProveedores(03,'3267676', 'Pescadito', 'Ruiz', 'Zona 17', 'Vender', '21212121', 'elmejordeGT.com');
call sp_AgregarProveedores(04,'3232422', 'Pedri', 'Gonzales', 'Zona 8', 'Vender', '54665656', 'elcanariodelFCB.com')

-- ----------------------------------- Listar Proveedores -------------------------------------------------------------------
Delimiter $$
	Create procedure sp_ListarProveedores()
		Begin
			Select
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P;
		end $$
Delimiter ;

call sp_ListarProveedores();
-- ---------------------------------Buscar Proveedores ----------------------------------------------------------------------
Delimiter $$
	Create procedure sp_BuscarProveedor(in codProvee int)
		Begin
			Select 
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P
            where codigoProveedor = codProvee;
		end $$
Delimiter ;

call sp_BuscarProveedor(01);

-- --------------------------------------------- Eliminar Proveedor -----------------------------------------------------------
Delimiter $$
	Create procedure sp_EliminarProveedor(in codPro int)
		Begin
			Delete from Proveedores
            where codigoProveedor = codPro;
	end$$
Delimiter ;

call sp_EliminarProveedor(02);

-- -----------------------------------------Editar Proveedor --------------------------------------------------
Delimiter $$
	create procedure sp_EditarProveedor(in _codigoProveedor int, in _NITproveedor varchar(10), in _nombreProveedor varchar(60),
    in _apellidoProveedor varchar(60), in _direccionProveedor varchar(150), in _razonSocial varchar(60), in _contactoPrincipal varchar(50), in _paginaWeb varchar (50))
		Begin
			update Proveedores
				set
                Proveedores.codigoProveedor = _codigoProveedor,
                Proveedores.NITproveedor = _NITproveedor,
                Proveedores.nombreProveedor = _nombreProveedor,
                Proveedores.apellidoProveedor = _apellidoProveedor,
                Proveedores.direccionProveedor = _direccionProveedor,
                Proveedores.razonSocial = _razonSocial,
                Proveedores.contactoPrincipal = contactoPrincipal,
                Proveedores.paginaWeb = _paginaWeb
                where proveedores.codigoProveedor = _codigoProveedor;
	end $$
Delimiter ;

call sp_EditarProveedor(01,'18122022','Leo','Messirve','Zona 10','Vender','10101919','elmejordelahistoria10.com');
call sp_ListarProveedores();

-- ---------------------  Procesedimientos Almacenados de Cargo Empleado --------------------------------
-- ---------------------- Agregar Cargo Empleado -------------------------------------------------------
Delimiter $$
	create procedure sp_agregarCargoEmpleado (in codigoCargoEmpleado int, in nombreCargo varchar(45), in descripcionCargo varchar(45))
		begin
		insert into CargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo) 
        values (codigoCargoEmpleado, nombreCargo, descripcionCargo);
	end $$
Delimiter ;
    
call sp_agregarCargoEmpleado (01,'Gerente', 'Supervisa a los supervisores');
call sp_agregarCargoEmpleado (02,'Supervisor', 'Supervisa a diferentes empleados');
call sp_agregarCargoEmpleado (03, 'Cajero', 'Esta encargado de la caja');

-- ---------------------- Listar Cargo Empleado -------------------------------------------------------
Delimiter $$
	create procedure sp_ListarCargoEmpleado()
		begin
			select
			CaE.codigoCargoEmpleado,
			CaE.nombreCargo,
			CaE.descripcionCargo
			from CargoEmpleado CaE;
	end $$
Delimiter ;

call sp_ListarCargoEmpleado();

-- ---------------------- Buscar Cargo Empleado -------------------------------------------------------
Delimiter $$
	create procedure sp_BuscarCargoEmpleado (in codCaEm int)
		begin
			select
			CaE.codigoCargoEmpleado,
			CaE.nombreCargo,
			CaE.descripcionCargo
			from CargoEmpleado CaE
			where codigoCargoEmpleado = codCaEm;
	end $$
delimiter ;

call sp_BuscarCargoEmpleado(01);

-- ---------------------- Eliminar Cargo Empleado -------------------------------------------------------
Delimiter $$
	create procedure sp_EliminarCargoEmpleado(in codEmple int)
		begin
			delete from CargoEmpleado
			where codigoCargoEmpleado = codEmple;
	end $$
delimiter ;

call sp_EliminarCargoEmpleado(02);

-- ---------------------- Editar Cargo Empleado -------------------------------------------------------
Delimiter $$
	create procedure sp_EditarCargoEmpleado(in codEmpleado int, in nomCargo varchar(45), in deCargo varchar(45))
		begin
		update CargoEmpleado CargoEmpleado
			set
            CargoEmpleado.codigoCargoEmpleado = codEmpleado,
			CargoEmpleado.nombreCargo = nomCargo,
			CargoEmpleado.descripcionCargo = deCargo
			where codigoCargoEmpleado = codEmpleado;
	end $$
delimiter ;       
         
call sp_EditarCargoEmpleado(01, 'Gerente General','Supervisa a todos los gerentes');
call sp_ListarCargoEmpleado();
-- ----------------------------- Procedimiento Almacenado ---------------------------------
-- ---------------------------- TipoProducto ----------------------------------------------

-- --------------------------- Agregar Tipo Producto-------------------------------------
Delimiter $$
	create procedure Sp_agregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
		begin
		insert into TipoProducto(codigoTipoProducto,descripcion) 
        values (codigoTipoProducto,descripcion);
	end $$
Delimiter ;

call Sp_agregarTipoProducto (01, 'Es un producto alimenticio');
call Sp_agregarTipoProducto (02, 'Es un producto de tipo alcholico');
call Sp_agregarTipoProducto (03, 'Es un producto de limpieza');
call Sp_agregarTipoProducto (04, 'Es un producto escolar');

-- --------------------------- Listar Tipo Producto-------------------------------------
Delimiter $$
	create procedure sp_ListarTipoProducto()
		begin
			select
			TP.codigoTipoProducto,
			TP.descripcion
			from TipoProducto TP;
	end $$
Delimiter ;

call sp_ListarTipoProducto();

-- --------------------------- Buscar Tipo Producto-------------------------------------
Delimiter $$
	create procedure sp_BuscarTipoProducto (in codTiPro int)
		begin
			select
			TP.codigoTipoProducto,
			TP.descripcion
			from TipoProducto TP
			where codigoTipoProducto = codTiPro;
	end $$
delimiter ;

call sp_BuscarTipoProducto(01);

-- --------------------------- Eliminar Tipo Producto-------------------------------------
Delimiter $$
	create procedure sp_EliminarTipoProducto(in codTiPro int)
		begin
			delete from TipoProducto
			where codigoTipoProducto = codTiPro;
	end $$
delimiter ;

call sp_EliminarTipoProducto(02);

-- --------------------------- Editar Tipo Producto-------------------------------------
Delimiter $$
	Create procedure sp_EditarTipoProducto(in codPro int, in descrip varchar(45))
		begin
			update TipoProducto TP
			set
            TP.codigoTipoProducto = codPro,
			TP.descripcion = descrip
			where codigoTipoProducto = codPro;
	end $$
delimiter ;
          
call sp_EditarTipoProducto(01,'Es un producto bebible');
call sp_ListarTipoProducto();

-- ---------------------------- Procedimiento Almacenado---------------------------------
-- ---------------------------- Compras ------------------------------------------------

-- --------------------------- Agregar Compras -------------------------------------
Delimiter $$
	create procedure Sp_agregarCompras (in numeroDocumento int, in fechaDocumento date, in descripcion varchar (60), in totalDocumento decimal(10,2))
		begin
		insert into Compras (numeroDocumento,fechaDocumento,descripcion,totalDocumento) 
        values (numeroDocumento,fechaDocumento,descripcion,totalDocumento);
	end $$
Delimiter ;

call Sp_agregarCompras (01, '2024-01-22', 'compra realisada', 25.05);
call Sp_agregarCompras (02, '2024-02-23', 'compra realisada', 22.50);
call Sp_agregarCompras (03, '2022-01-02', 'compra no realisada', 15.50);

-- --------------------------- Listar Compras -------------------------------------
Delimiter $$
	create procedure sp_ListarCompras()
		begin
			select
			Comp.numeroDocumento,
			Comp.fechaDocumento,
			Comp.descripcion,
			Comp.totalDocumento
		from Compras Comp;
	end $$
Delimiter ;

call sp_ListarCompras();

-- --------------------------- Buscar Compras -------------------------------------
Delimiter $$
	create procedure sp_BuscarCompras (in codComp int)
		begin
			select
			Comp.numeroDocumento,
			Comp.fechaDocumento,
			Comp.descripcion,
			Comp.totalDocumento
			from Compras Comp
			where numeroDocumento = codComp;
	end $$
delimiter ;

call sp_BuscarCompras(01);

-- --------------------------- Eliminar Compras -------------------------------------
Delimiter $$
	create procedure sp_EliminarCompras(in codComp int)
		begin
			delete from Compras
			where numeroDocumento = codComp;
	end $$
delimiter ;

call sp_EliminarCompras(02);

-- --------------------------- Editar Compras -------------------------------------
Delimiter $$
	create procedure sp_EditarCompras(in numDocumento int, in fDocument date, in descrip varchar (60), in toDocumento decimal(10,2))
		begin
			update Compras Comp
				set
                Comp.numeroDocumento = numDocumento,
				Comp.fechaDocumento = fDocument,
				Comp.descripcion = descrip,
				Comp.totalDocumento = toDocumento
				where numeroDocumento = numDocumento;
		end $$
delimiter ;    
            
call sp_EditarCompras(01, '2024-01-23' , 'No se pudo realizar', 30.55);
call sp_ListarCompras();

-- --------------------------- Procedimiento Almacenado -----------------------------
-- --------------------------- Email Proveedor --------------------------------------

-- --------------------------- Agregar Email Proveedor -----------------------------
Delimiter $$
	create procedure sp_agregarEmailProveedor (in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar (100), in codigoProveedor int)
		begin
		insert into EmailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor) 
        values (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor);
	end $$
Delimiter ;

call sp_agregarEmailProveedor(01, 'lionesmessi10@gmail.com', 'Correo de trabajo', 01);
call sp_agregarEmailProveedor(02, 'pescadito21@gmail.com', 'Correo de institucional', 03);

-- --------------------------- Editar Email Proveedor -------------------------------
Delimiter $$
	create procedure sp_ListarEmailProveedor()
		begin
			select
			EmailP.codigoEmailProveedor,
			EmailP.emailProveedor,
			EmailP.descripcion,
			EmailP.codigoProveedor
		from EmailProveedor EmailP;
	end $$
Delimiter ;

call sp_ListarEmailProveedor();

-- --------------------------- Buscar Email Proveedor -----------------------------
Delimiter $$
	create procedure sp_BuscarEmailProvedor (in codEmailP int)
		begin
			select
			EmailP.codigoEmailProveedor,
			EmailP.emailProveedor,
			EmailP.descripcion,
			EmailP.codigoProveedor
			from EmailProveedor EmailP
			where codigoEmailProveedor = codEmailP;
	end $$
delimiter ;

call sp_BuscarEmailProvedor(01);

-- ---------------------------- Eliminar Email Proveedor ----------------------------
Delimiter $$
	create procedure sp_EliminarEmailProveedor(in codEmailP int)
		begin
			delete from EmailProveedor
			where codigoEmailProveedor = codEmailP;
	end $$
delimiter ;

call sp_EliminarEmailProveedor(02);

-- --------------------------- Editar Email Proveedor -------------------------------
Delimiter $$
	create procedure sp_EditarEmailProveedor(in codEmailP int, in emailP varchar(50), in descrip varchar (100), in codigoP int)
		begin
			update EmailProveedor EmailP
				set
                EmailP.codigoEmailProveedor = codEmailP,
				EmailP.emailProveedor = emailP,
				EmailP.descripcion = descrip,
				EmailP.codigoProveedor = codigoP
				where codigoEmailProveedor = codEmailP;
		end $$
delimiter ;   

call sp_EditarEmailProveedor(01, 'elgoat10@colegio.com', 'Correo del colegio', 01);
call sp_ListarEmailProveedor();

-- --------------------------- Procedimiento Almacenado -----------------------------
-- --------------------------- Telefono Proveedor --------------------------------------

-- --------------------------- Agregar Telefono Proveedor -----------------------------
Delimiter $$
	create procedure sp_agregarTelefonoProveedor (in codigoTelefonoProveedor int, in numeroPrincipal varchar(8), in numeroSecundario varchar (8), observaciones varchar (45), in codigoProveedor int)
		begin
		insert into TelefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor) 
        values (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor);
	end $$
Delimiter ;

call sp_agregarTelefonoProveedor(01, '12123434', '98987676', 'El primer numero es personal', 01);
call sp_agregarTelefonoProveedor(02, '56567889', '45457698', 'El segundo numero es de casa', 03);

-- --------------------------- Listar Telefono Proveedor -------------------------------
Delimiter $$
	create procedure sp_ListarTelefonoProveedor()
		begin
			select
			TelefonoP.codigoTelefonoProveedor,
			TelefonoP.numeroPrincipal,
			TelefonoP.numeroSecundario,
			TelefonoP.observaciones,
            TelefonoP.codigoProveedor
		from TelefonoProveedor TelefonoP;
	end $$
Delimiter ;

call sp_ListarTelefonoProveedor();

-- --------------------------- Buscar Telefono Proveedor -----------------------------
Delimiter $$
	create procedure sp_BuscarTelefonoProvedor (in codTelefonoP int)
		begin
			select
			TelefonoP.codigoTelefonoProveedor,
			TelefonoP.numeroPrincipal,
			TelefonoP.numeroSecundario,
			TelefonoP.observaciones,
            TelefonoP.codigoProveedor
			from TelefonoProveedor TelefonoP
			where codigoTelefonoProveedor = codTelefonoP;
	end $$
delimiter ;

call sp_BuscarTelefonoProvedor(01);

-- ---------------------------- Eliminar Telefono Proveedor ----------------------------
Delimiter $$
	create procedure sp_EliminarTelefonoProveedor(in codTelefonoP int)
		begin
			delete from TelefonoProveedor
			where codigoTelefonoProveedor = codTelefonoP;
	end $$
delimiter ;

call sp_EliminarTelefonoProveedor(02);

-- --------------------------- Editar Telefono Proveedor -------------------------------
Delimiter $$
	create procedure sp_EditarTelefonoProveedor(in codTelefonoP int, in numPri varchar(8), in numSec varchar (8), in obser varchar(45), in codigoP int)
		begin
			update TelefonoProveedor TelefonoP
				set
                TelefonoP.codigoTelefonoProveedor = codTelefonoP,
				TelefonoP.numeroPrincipal = numPri,
                TelefonoP.numeroSecundario = numSec,
				TelefonoP.observaciones = obser,
				TelefonoP.codigoProveedor = codigoP
				where codigoTelefonoProveedor = codTelefonoP;
		end $$
delimiter ;   

call sp_EditarTelefonoProveedor(01, '76768910', '12767612', 'El segundo numero es de casa', 01);
call sp_ListarTelefonoProveedor();

-- --------------------------- Procedimiento Almacenado -----------------------------
-- --------------------------- Productos --------------------------------------

-- --------------------------- Agregar Productos ------------------------------
Delimiter $$
	create procedure sp_agregarProductos (in codigoProducto int, in descripcion varchar (45), in precioUnitario decimal(10, 2), in precioDocena decimal(10, 2),
    in precioMayor decimal(10, 2), in imagenProducto varchar(45), in existencia int, in codigoTipoProducto int, in codigoProveedor int)
		begin
		insert into Productos (codigoProducto, descripcion, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor) 
        values (codigoProducto, descripcion, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, codigoTipoProducto, codigoProveedor);
	end $$
Delimiter ;

call sp_agregarProductos(01, 'Es una pizza mediana', 40.00, 30.50, 20.30, 'PizzaMediana.png', 20, 01, 01);
call sp_agregarProductos(02, 'Es un detergente', 45.00, 32.50, 25.30, 'Detergente.png', 35, 03, 03);
call sp_agregarProductos(03, 'Es un lapiz', '15.99', 10.99, 6.99, 'Lapiz.png', '100', 03, 03);

-- --------------------------- Listar Productos -------------------------------
Delimiter $$
	create procedure sp_ListarProductos()
		begin
			select
			Productos.codigoProducto,
			Productos.descripcion,
			Productos.precioUnitario,
			Productos.precioDocena,
            Productos.precioMayor,
            Productos.imagenProducto,
            Productos.existencia,
            Productos.codigoTipoProducto,
            Productos.codigoProveedor
		from Productos Productos;
	end $$
Delimiter ;

call sp_ListarProductos();

-- --------------------------- Buscar Productos -----------------------------
Delimiter $$
	create procedure sp_BuscarProducto (in codProducto int)
		begin
			select
			Productos.codigoProducto,
			Productos.descripcion,
			Productos.precioUnitario,
			Productos.precioDocena,
            Productos.precioMayor,
            Productos.imagenProducto,
            Productos.existencia,
            Productos.codigoTipoProducto,
            Productos.codigoProveedor
			from Productos Productos
			where codigoProducto = codProducto;
	end $$
delimiter ;

call sp_BuscarProducto(01);

-- ---------------------------- Eliminar Producto ----------------------------
Delimiter $$
	create procedure sp_EliminarProductos(in codProducto int)
		begin
			delete from Productos
			where codigoProducto = codProducto;
	end $$
delimiter ;

call sp_EliminarProductos(02);

-- --------------------------- Editar Productos -------------------------------
Delimiter $$
	create procedure sp_EditarProductos(in codProducto int, in descrip varchar(45), in precioU decimal(10, 2), in precioD decimal(10, 2), in precioM decimal(10, 2),
    in imagenP varchar(45), in exis int, in codTipoP int, in codPro int)
		begin
			update Productos Produ
				set
                Produ.codigoProducto = codProducto,
                Produ.descripcion = descrip,
				Produ.precioUnitario = precioU,
				Produ.precioDocena = precioD,
                Produ.precioMayor = precioM,
                Produ.imagenProducto = imagenP,
                Produ.existencia = exis,
                Produ.codigoTipoProducto = codTipoP,
                Produ.codigoProveedor = codPro
				where codigoProducto = codProducto;
		end $$
delimiter ;   

call sp_EditarProductos(01, 'Es una hamburguesa', '30.75', '20.50', '15.00', 'hamburguesa.png', 40, 01, 01);
call sp_ListarProductos();

-- --------------------------- Procedimiento Almacenado -----------------------------
-- --------------------------- 	Detalle Compra --------------------------------------

-- --------------------------- Agregar Detalle Compra ------------------------------
Delimiter $$
	create procedure sp_agregarDetalleCompra (in codigoDetalleCompra int, in costoUnitario decimal(10, 2), in cantidad int, in codigoProducto int, in numeroDocumento int)
		begin
		insert into DetalleCompra (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento) 
        values (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento);
	end $$
Delimiter ;

call sp_agregarDetalleCompra(01, 30.00, 45 ,01, 01);
call sp_agregarDetalleCompra(02, 15.99, 100 ,03, 03);

-- --------------------------- Listar Detalle Compra -------------------------------
Delimiter $$
	create procedure sp_ListarDetalleCompra()
		begin
			select
			DetalleC.codigoDetalleCompra,
			DetalleC.costoUnitario,
			DetalleC.cantidad,
			DetalleC.codigoProducto,
            DetalleC.numeroDocumento
		from DetalleCompra DetalleC;
	end $$
Delimiter ;

call sp_ListarDetalleCompra();

-- --------------------------- Buscar Detalle Compra -----------------------------
Delimiter $$
	create procedure sp_BuscarDetalleCompra (in codDetalleC int)
		begin
			select
			DetalleC.codigoDetalleCompra,
			DetalleC.costoUnitario,
			DetalleC.cantidad,
			DetalleC.codigoProducto,
            DetalleC.numeroDocumento
			from DetalleCompra DetalleC
			where codigoDetalleCompra = codDetalleC;
	end $$
delimiter ;

call sp_BuscarDetalleCompra(01);

-- ---------------------------- Eliminar Detalle Compra ----------------------------
Delimiter $$
	create procedure sp_EliminarDetalleCompra(in codDetalleC int)
		begin
			delete from DetalleCompra
			where codigoDetalleCompra = codDetalleC;
	end $$
delimiter ;

call sp_EliminarDetalleCompra(02);

-- --------------------------- Editar Detalle Compra -------------------------------
Delimiter $$
	create procedure sp_EditarDetalleCompra(in codDetalleC int, in costoU decimal(10, 2), in cantidad int, in codProdu int, in numDoc int)
		begin
			update DetalleCompra DetalleC
				set
                DetalleC.codigoDetalleCompra = codDetalleC,
                DetalleC.costoUnitario = costoU,
				DetalleC.cantidad = cantidad,
				DetalleC.codigoProducto = codProdu,
                DetalleC.numeroDocumento = numDoc
				where codigoDetalleCompra = codDetalleC;
		end $$
delimiter ;   

call sp_EditarDetalleCompra(01, 35.99, 76, 01, 01);
call sp_ListarDetalleCompra();

-- --------------------------- Procedimiento Almacenado -----------------------------
-- --------------------------- 	Empleados --------------------------------------

-- --------------------------- Agregar Empleados ------------------------------
Delimiter $$
	create procedure sp_agregarEmpleados (in codigoEmpleado int, in nombresEmpleado varchar(50), in apellidosEmpleado varchar(50), in sueldo decimal(10, 2), in direccion varchar(150),
    in turno varchar(15), in codigoCargoEmpleado int)
		begin
		insert into Empleados (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado) 
        values (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado);
	end $$
Delimiter ;

call sp_agregarEmpleados(01, 'Wicho', 'Hernandez' , 10000.50, 'En su casa', 'Vespertina', 01);
call sp_agregarEmpleados(02, 'Neto', 'Bran' , 2500.50, 'En su hogar', 'Matutina', 03);
call sp_agregarEmpleados(03, 'Jose', 'Pirulo', 3033.08, 'Estadio los rojos', 'Vespertina', 01);

-- --------------------------- Listar Empleados -------------------------------
Delimiter $$
	create procedure sp_ListarEmpleados()
		begin
			select
			Emple.codigoEmpleado,
			Emple.nombresEmpleado,
			Emple.apellidosEmpleado,
			Emple.sueldo,
            Emple.direccion,
            Emple.turno,
            Emple.codigoCargoEmpleado
		from Empleados Emple;
	end $$
Delimiter ;

call sp_ListarEmpleados();

-- --------------------------- Buscar Empleados -----------------------------
Delimiter $$
	create procedure sp_BuscarEmpleados (in codEmple int)
		begin
			select
			Emple.codigoEmpleado,
			Emple.nombresEmpleado,
			Emple.apellidosEmpleado,
			Emple.sueldo,
            Emple.direccion,
            Emple.turno,
            Emple.codigoCargoEmpleado
			from Empleados Emple
			where codigoEmpleado = codEmple;
	end $$
delimiter ;

call sp_BuscarEmpleados(01);

-- ---------------------------- Eliminar Empleados ----------------------------
Delimiter $$
	create procedure sp_EliminarEmpleados(in codEmple int)
		begin
			delete from Empleados
			where codigoEmpleado = codEmple;
	end $$
delimiter ;

call sp_EliminarEmpleados(02);

-- --------------------------- Editar Empleados -------------------------------
Delimiter $$
	create procedure sp_EditarEmpleados(in codEmple int, in nomEmple varchar(50), in apeEmple varchar(50), in suel decimal(10, 2), in dire varchar(150), 
    in tur varchar(15), in codCargoE int)
		begin
			update Empleados Emple
				set
                Emple.codigoEmpleado = codEmple,
                Emple.nombresEmpleado = nomEmple,
				Emple.apellidosEmpleado = apeEmple,
				Emple.sueldo = suel,
                Emple.direccion = dire,
                Emple.turno = tur,
                Emple.codigoCargoEmpleado = codCargoE
				where codigoEmpleado = codEmple;
		end $$
delimiter ;   

call sp_EditarEmpleados(01, 'Luis', 'Garcia' , 10001.50, 'En su mansion', 'Matutina', 01);
call sp_ListarEmpleados();

-- --------------------------- Procedimiento Almacenado -----------------------------
-- --------------------------- Factura --------------------------------------

-- --------------------------- Agregar Factura ------------------------------
Delimiter $$
	create procedure sp_agregarFactura (in numeroFactura int, in estado varchar(50), in totalFactura decimal(10, 2), in fechaFactura date, 
    in codigoCliente int, in codigoEmpleado int)
		begin
		insert into Factura (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado) 
        values (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado);
	end $$
Delimiter ;

call sp_agregarFactura(01, 'pagada', 102.99, '2024-01-01', 01, 01);
call sp_agregarFactura(02, 'pagada', 56.98, '2023-02-02', 01, 03);
call sp_agregarFactura(03, 'pagada', 49.86, '2024-06-06', 01, 03);

-- --------------------------- Listar Factura -------------------------------
Delimiter $$
	create procedure sp_ListarFactura()
		begin
			select
			Factu.numeroFactura,
			Factu.estado,
			Factu.totalFactura,
			Factu.fechaFactura,
            Factu.codigoCliente,
            Factu.codigoEmpleado
		from Factura Factu;
	end $$
Delimiter ;

call sp_ListarFactura();

-- --------------------------- Buscar Factura -----------------------------
Delimiter $$
	create procedure sp_BuscarFactura (in numFact int)
		begin
			select
			Factu.numeroFactura,
			Factu.estado,
			Factu.totalFactura,
			Factu.fechaFactura,
            Factu.codigoCliente,
            Factu.codigoEmpleado
			from Factura Factu
			where numeroFactura = numFact;
	end $$
delimiter ;

call sp_BuscarFactura(01);

-- ---------------------------- Eliminar Factura ----------------------------
Delimiter $$
	create procedure sp_EliminarFactura(in numFact int)
		begin
			delete from Factura
			where numeroFactura = numFact;
	end $$
delimiter ;

call sp_EliminarFactura(02);

-- --------------------------- Editar Factura -------------------------------
Delimiter $$
	create procedure sp_EditarFactura(in numFact int, in est varchar(50), in totalF decimal(10, 2), in fechaF date, in codClie int, in codEmple int)
		begin
			update Factura Fact
				set
                Fact.numeroFactura = numFact,
                Fact.estado = est,
				Fact.totalFactura = totalF,
				Fact.fechaFactura = fechaF,
                Fact.codigoCliente = codClie,
                Fact.codigoEmpleado = codEmple
				where numeroFactura = numFact;
		end $$
delimiter ;   

call sp_EditarFactura(01, 'pagada', 106.99, '2024-02-08', 01, 01);
call sp_ListarFactura();

-- --------------------------- Procedimiento Almacenado -----------------------------
-- ---------------------------  Detalle Factura --------------------------------------

-- --------------------------- Agregar Detalle Factura ------------------------------
Delimiter $$
	create procedure sp_agregarDetalleFactura (in codigoDetalleFactura int, in precioUnitario decimal(10, 2), in cantidad int, in numeroFactura int, in codigoProducto int)
		begin
		insert into DetalleFactura (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto) 
        values (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto);
	end $$
Delimiter ;

call sp_agregarDetalleFactura(01, 30.50, 5, 01, 01);
call sp_agregarDetalleFactura(02, 15.50, 10, 03, 03);

-- --------------------------- Listar Detalle Factura -------------------------------
Delimiter $$
	create procedure sp_ListarDetalleFactura()
		begin
			select
			DetalleF.codigoDetalleFactura,
			DetalleF.precioUnitario,
			DetalleF.cantidad,
			DetalleF.numeroFactura,
            DetalleF.codigoProducto
		from DetalleFactura DetalleF;
	end $$
Delimiter ;

call sp_ListarDetalleFactura();

-- --------------------------- Buscar Detalle Factura -----------------------------
Delimiter $$
	create procedure sp_BuscarDetalleFactura (in numDetalleF int)
		begin
			select
			DetalleF.codigoDetalleFactura,
			DetalleF.precioUnitario,
			DetalleF.cantidad,
			DetalleF.numeroFactura,
            DetalleF.codigoProducto
			from DetalleFactura DetalleF
			where codigoDetalleFactura = numDetalleF;
	end $$
delimiter ;

call sp_BuscarDetalleFactura(01);

-- ---------------------------- Eliminar Detalle Factura ----------------------------
Delimiter $$
	create procedure sp_EliminarDetalleFactura(in numDetalleF int)
		begin
			delete from DetalleFactura
			where codigoDetalleFactura = numDetalleF;
	end $$
delimiter ;

call sp_EliminarDetalleFactura(02);

-- --------------------------- Editar Detalle Factura -------------------------------
Delimiter $$
	create procedure sp_EditarDetalleFactura(in numDetalleF int, in precioU decimal(10, 2), in cant int, in numF int, in codPro int)
		begin
			update DetalleFactura DetalleF
				set
                DetalleF.codigoDetalleFactura = numDetalleF,
                DetalleF.precioUnitario = precioU,
				DetalleF.cantidad = cant,
				DetalleF.numeroFactura = numF,
                DetalleF.codigoProducto = codPro
				where codigoDetalleFactura = numDetalleF;
		end $$
delimiter ;   

call sp_EditarDetalleFactura(01, 34.89, 6, 01, 01);
call sp_ListarDetalleFactura();

/*-- funcion sobre el precio unitario
delimiter $$
	create function fn_TraerPrecioUnitario(codProd varchar(15)) returns decimal(10, 2)
	deterministic
	begin
		declare precio decimal(10, 2);
		set precio = (select detallecompra.costoUnitario from detallecompra
			where detallecompra.codigoProducto-codPro);
			return precio;
	end $$
delimiter ;

-- total compra
delimiter $$
	create function fn_TotalCompra(numeroDocumento int) returns decimal(10,2)
    deterministic
    begin
		declare sumatoria decimal(10, 2);
        
        set sumatoria = (select sum(cantidad*costoUnitario) from detallecompra
			where numeroDocumento = numeroDocumento);
			return sumatoria;
	end $$

-- Precio Detalle Factura
delimiter $$
	create trigger tr_insertarPrecioDetalleFactura_Before_Insert
    before insert on detallefactura
    for each row
		begin
			declare total decimal(10,2);
           set new.precioUnitario = (select precioUnitario from productos
									where productos.codigoProducto-new.codigoProducto); 
    end $$
delimiter ;*/

-- Insertar precios en Productos
/*delimiter $$
	create trigger tr_insertarPreciosProductos_after_insert
    after insert on detallecompra
    for each row
		begin
        call sp_EditarProductos(new.codigoProductos,
								)*/