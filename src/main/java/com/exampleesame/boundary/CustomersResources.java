package com.exampleesame.boundary;

import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.stream.JsonCollectors;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.exampleesame.entity.Customer;
import com.exampleesame.store.CustomerStore;

@Path("customers")
@Tag(name = "Gestione Customers", description = "Permette di gestire i clienti")
@DenyAll
public class CustomersResources {
    
    @Inject
    private CustomerStore storecustomer;
       
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce l'elenco di tutti i clienti")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Elenco ritornato con successo"),
        @APIResponse(responseCode = "404", description = "Elenco non trovato")
    })
    @RolesAllowed({"Customer", "Admin"})  // Più flessibilità di accesso
    public List<Customer> all(@DefaultValue("1") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("size") int size) {
        return storecustomer.all();
    }
    
    
    @GET
    @Path("allslice")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce l'elenco con informazioni ridotte di tutti i clienti")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Elenco ritornato con successo"),
        @APIResponse(responseCode = "404", description = "Elenco non trovato")
    })
    @PermitAll
    public JsonArray allSlice() {
        return storecustomer.all().stream().map(Customer::toJsonSliceName).collect(JsonCollectors.toJsonArray());
    }
    
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce il cliente identificato dall'ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Utente ritornato con successo"),
        @APIResponse(responseCode = "404", description = "Utente non trovato")
    })
    @RolesAllowed({"Customer", "Admin"})  // Anche Admin può accedere
    public Customer find(@PathParam("id") Long id) {
        return storecustomer.find(id).orElseThrow(() -> new NotFoundException("customer non trovato. id=" + id));
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Permette la registrazione di un nuovo cliente")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Nuovo cliente creato con successo"),
        @APIResponse(responseCode = "404", description = "Creazione del cliente fallito")
    })
    @PermitAll
    public Response create(@Valid Customer entity) {
        
        if(storecustomer.findCustomerbyCustomerName(entity.getCustomerName()).isPresent()){
            
           return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        
        Customer saved = storecustomer.save(entity);
        
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
}
    
    @DELETE
    @Path("{id}")
    @Operation(description = "Elimina un cliente tramite l'ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Cliente eliminato con successo"),
        @APIResponse(responseCode = "404", description = "Cliente non trovato")
    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")  // Solo Admin può cancellare
    public Response delete(@PathParam("id") Long id) {
        Customer found = storecustomer.find(id).orElseThrow(() -> new NotFoundException("customer non trovato. id=" + id));
        storecustomer.remove(found);
        return Response.status(Response.Status.OK).build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Aggiorna i dati del cliente")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Cliente aggiornato con successo"),
        @APIResponse(responseCode = "404", description = "Cliente non trovato"),
        @APIResponse(responseCode = "500", description = "Errore durante l'aggiornamento")
    })
    @RolesAllowed("Admin")
    public Response update(@PathParam("id") Long id, @Valid Customer entity) {
        Customer found = storecustomer.find(id).orElseThrow(() -> new NotFoundException("cliente non trovato. id=" + id));
        entity.setId(id);
        Customer updated = storecustomer.update(entity);
        return Response.status(Response.Status.OK)
                .entity(updated)
                .build();
    }
}