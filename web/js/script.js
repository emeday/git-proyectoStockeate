/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


console.log("Prueba");

//se dio click en el botón "agregar producto"
$("#form-addProd").submit(function(e) {
    e.preventDefault();
    
    //prerenderizamos la plantilla con handlebars
    var source = $("#plantilla-alerta").html();
    var templateMissions = Handlebars.compile(source);    
    
    $.ajax({
            url: "CRUDServlet?op=doAddProd",
            type: "get",
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            data: $('#form-addProd').serialize(),
            beforeSend: function() {
                $('.cargando').css('visibility', 'visible'); // Muestro animacion de carga
            }
        })
        // Cuando la petición de los datos fue correcta, mediante el método 'done' imprimo los datos
        .done(function(d) {
            console.log(d);
            //se prerenderiza la data en el div con clase .render-alerta
            var template = templateMissions(d);
            $('.render-alerta').append(template);
            $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga
            $("#form-addProd").trigger('reset');
        })
        // Este método es para manejar algún error al obtener los datos 
        .fail(function(d) {
            console.log("Hubo un error: " + d);
            $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga
        });    
});

//se dio click en el botón "editar producto"
$("#form-editProd").submit(function(e) {
    e.preventDefault();
    
    var source = $("#plantilla-alerta").html();
    var templateMissions = Handlebars.compile(source);    
    
    $.ajax({
            url: "CRUDServlet?op=doEditProd",
            type: "get",
            dataType: "json",
            contentType: 'application/json; charset=UTF-8',
            data: $('#form-editProd').serialize(),
            beforeSend: function() {
                $('.cargando').css('visibility', 'visible'); // Muestro animacion de carga
            }
        })
        // Cuando la petición de los datos fue correcta, mediante el método 'done' imprimo los datos
        .done(function(d) {
            console.log(d);
            var template = templateMissions(d);
            $('.render-alerta').append(template);
            $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga
            //$("#form-addProd").trigger('reset');
        })
        // Este método es para manejar algún error al obtener los datos 
        .fail(function(d) {
            console.log("Hubo un error: " + d);
            $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga
        });     
});

//buscador de productos
var searchRequest = null;
$(document).ready(function () {
    //minimo de caracteres para iniciar la búsqueda
    var minlength = 0;
    
    $("#buscador-main").keyup(function (){
        var that = this;
        value = $(this).val();
        //prerenderizamos la plantilla con handlebars
        var source = $("#plantilla-listaProd").html();
        var templateMissions = Handlebars.compile(source);        
        
        //si el valor es mayor al mínimo establecido
        if (value.length >= minlength){ 
            //Se agregó la variable searchRequest para evitar solicitudes innecesarias al servidor
            if(searchRequest !== null){
                searchRequest.abort();
            }
            //serealizo el valor del campo de búsqueda
            var nombreSearch = $('input[name=nombreSearch]');
            var data = 'nombreSearch=' + nombreSearch.val();            
            
            //llamamos a la opción listar con ajax
            $.ajax({
                    url: "CRUDServlet?op=listar",
                    type: "get",
                    dataType: "json",
                    data: data,
                    beforeSend: function() {
                        $('.cargando').css('visibility', 'visible'); // Muestro animacion de carga
                    }
                })
                // Cuando la petición de los datos fue correcta, mediante el método 'done' imprimo los datos
                .done(function(d) {                   
                    if (value===$(that).val()){
                        console.log(d);  
                        //se prerenderiza la data en el div
                        var template = templateMissions(d);
                        $('.tblListaProd').html("");
                        $('.render-listaProd').append(template);
                        $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga                
                    }
                })
                // Este método es para manejar algún error al obtener los datos 
                .fail(function(d) {
                    console.log("Hubo un error: " + d);
                    $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga
                });                   
        }
    }).keyup();  
});

//buscador de movimientos
var searchMovimiento = null;
$(document).ready(function () {
    //minimo de caracteres para iniciar la búsqueda
    var minlength = 0;
    
    $("#buscador-mov").keyup(function (){
        var that = this;
        value = $(this).val();
        //prerenderizamos la plantilla con handlebars
        var source = $("#plantilla-listaMov").html();
        var templateMissions = Handlebars.compile(source);        
        
        //si el valor es mayor al mínimo establecido
        if (value.length >= minlength){ 
            //Se agregó la variable searchRequest para evitar solicitudes innecesarias al servidor
            if(searchMovimiento !== null){
                searchMovimiento.abort();
            }
            //serealizo el valor del campo de búsqueda
            var movimientoSearch = $('input[name=movimientoSearch]');
            var selectTipoBusqueda = $('#selectTipoBusqueda');
            var data = 'selectTipoBusqueda=' + selectTipoBusqueda.val() + '&movimientoSearch=' + movimientoSearch.val();            
            
            //llamamos a la opción listar con ajax
            $.ajax({
                    url: "CRUDServlet?op=doTrace",
                    type: "get",
                    dataType: "json",
                    data: data,
                    beforeSend: function() {
                        $('.cargando').css('visibility', 'visible'); // Muestro animacion de carga
                    }
                })
                // Cuando la petición de los datos fue correcta, mediante el método 'done' imprimo los datos
                .done(function(d) {                   
                    if (value===$(that).val()){  
                        //se prerenderiza la data en el div
                        var template = templateMissions(d);
                        $('.tblListaMov').html("");
                        $('.render-listaMov').append(template);
                        $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga                
                    }
                })
                // Este método es para manejar algún error al obtener los datos 
                .fail(function(d) {
                    console.log("Hubo un error: " + d);
                    $('.cargando').css('visibility', 'hidden'); // Oculto animacion de carga
                });                   
        }
    }).keyup();  
});

//detecta si se ha dado click al boton de agregar/quitar stock
//recicla el modal para darle doble funcionalidad
//inserta fecha y hora dinámica en input
$(document).on('click', '.ioMovimiento', function(){
    window.setInterval(function() {
        var momentNow = moment();
        $('.iDateStock').val(momentNow.format('D/M/YYYY')+" - "+momentNow.format('HH:mm:ss'));         
    }, 100); 
    
    var tipoValue = $(this).data("tipo-value");
    var idValue = $(this).data("id-value");
    
    if (tipoValue === "entrada"){
        $("#modalIOStockTitulo").text("Agregar stock...");
        $("#modalIOStockDescripcion").text("Ingrese la entrada de stock:");
        $("label[for='inputStock']").text("Entrada:");
        $('input[name=inputTipoStock]').val(tipoValue);
        $('input[name=inputIdProd]').val(idValue);
        consultaProd(idValue);
        $('#inputStock').keyup(function(){
            setTotal("suma");
        });
    } else {
        $("#modalIOStockTitulo").text("Retirar stock...");
        $("#modalIOStockDescripcion").text("Ingrese la salida de stock:");
        $("label[for='inputStock']").text("Salida:"); 
        $('input[name=inputTipoStock]').val(tipoValue);
        $('input[name=inputIdProd]').val(idValue);        
        consultaProd(idValue);
        $('#inputStock').keyup(function(){
            setTotal("resta");
        });        
    }
});

//funcion consultar producto
function consultaProd(idProd){
    var data = 'codigo=' + idProd;    
    $.ajax({
            url: "CRUDServlet?op=infoInOutProd",
            type: "get",
            dataType: "json",
            data: data,
            beforeSend: function() {
                //sin animacion de carga
            }
        })
        // Cuando la petición de los datos fue correcta, mediante el método 'done' imprimo los datos
        .done(function(d) {
            $('input[name=nombreProducto]').val(d[0]['nombre']);
            $('input[name=hiddenOutputStock').val(d[0]['stock']);
            $('input[name=outputStock').val(d[0]['stock']);
        })
        // Este método es para manejar algún error al obtener los datos 
        .fail(function(d) {
            console.log("Hubo un error: " + d);
        });     
}

//se dio click en el submit de agregar o quitar stock
$("#form-addoutStock").submit(function(e){
    e.preventDefault();
    var source = $("#plantilla-alerta").html();
    var templateMissions = Handlebars.compile(source);     
    
    //serealizo el valor del campo de búsqueda
    var tipoStock = $('input[name=inputTipoStock]').val();
    var idProd = $('input[name=inputIdProd]').val();
    var newStock = $('input[name=inputStock]').val();
    var data = 'codigo=' + idProd + '&newStock=' + newStock + '&tipoOP=' + tipoStock; 
    
    $.ajax({
            url: "CRUDServlet?op=addoutStock",
            type: "get",
            dataType: "json",
            data: data,
            beforeSend: function() {
                //sin animacion de carga
            }
        })
        // Cuando la petición de los datos fue correcta, mediante el método 'done' imprimo los datos
        .done(function(d) {
            console.log(d);
            //se prerenderiza la data en el div con clase .render-alerta
            var template = templateMissions(d);
            $('.render-alerta').append(template);
            $('input[name=inputStock]').val('');
        })
        // Este método es para manejar algún error al obtener los datos 
        .fail(function(d) {
            console.log("Hubo un error: " + d);
            $('input[name=inputStock]').val('');
        });    
});


//helper para comparar una cadena de texto
Handlebars.registerHelper('if_eq', function(a, b, opts) {
    if(a === b)
        return opts.fn(this);
    else
        return opts.inverse(this);
});

//modal de bootstrap para darle funcionalidad al cuadro de confirmación "cancel-ok"
var confirmDelete = document.getElementById('confirm-delete');
if (confirmDelete){
    confirmDelete.addEventListener('shown.bs.modal', function (e) {
        var modal = $(this);
        var button = $(e.relatedTarget);
        var url = button.attr('data-bs-href');
        modal.find('.btn-ok').attr('href', url);
    });
}

//resetea el formulario del modal "agregarStock" al cerrarlo o dar close
$('#agregarStock').on('hidden.bs.modal', function () {
    $(this).find('form').trigger('reset');
    
    //si el box de stock agregado tiene contenido (se agregó stock)
    //borra el box y recarga la página para mostrar el producto con stock actualizado
    if ($(".render-alerta").contents().length !== 0){
        $(".render-alerta").html('');
        location.reload();        
    }
});

//
function setTotal(operacion) {
   var total = 0;
   
   var operador1 = parseInt($(".sumarestaInput").val());
   var operador2 = parseInt($(".actualStockInput").val());
   
    if (isNaN(operador1)){
        operador1 = 0;
    }
    
    if(operacion === 'suma'){
        total = operador1+operador2;
        
    } else if(operacion === 'resta'){
        total = operador2-operador1;
    }

   $("#outputStock").val(total);
};