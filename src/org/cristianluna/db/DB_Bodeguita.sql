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

call Sp_agregarTipoProducto (01,'Es un producto alimenticio');
call Sp_agregarTipoProducto (02,'Es un producto de tipo alcholico');

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

-- --------------------------- Eliminar Compras -------------------------------------
Delimiter $$
	create procedure sp_EditarCompras(in numDocumento int, in fDocument date, in descrip varchar (60), in toDocumento decimal(10,2))
		begin
			update Compras Comp
				set
				Comp.fechaDocumento = fDocument,
				Comp.descripcion = descrip,
				Comp.totalDocumento = toDocumento
				where numeroDocumento = numDocumento;
		end $$
delimiter ;    
            
call sp_EditarCompras(01, '2024-01-23' , 'No se pudo realizar', 30.05);
call sp_ListarCompras();