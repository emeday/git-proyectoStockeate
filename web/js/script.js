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
                        //console.log(d);  
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

//inserta fecha y hora dinámica en input
$(document).on('click', '.ioMovimiento', function(){
    window.setInterval(function() {
        var momentNow = moment();
        $('.iDateStock').val(momentNow.format('D/M/YYYY')+" - "+momentNow.format('HH:mm:ss'));         
    }, 100); 
});

//funcion para operar 2 inputs
function compute(operadorA, operadorB, operacion, salida) {
    var total;
    if ( $('input[name='+operadorA+']').val() !== undefined ) {
        var a = parseInt($('input[name='+operadorA+']').val());
        var b = operadorB;
        if (operacion === 'suma'){
            total = a+b;            
        } else if(operacion === 'resta'){
            total = a-b;
        }
        $('input[name='+salida+']').val(total);
    }
}