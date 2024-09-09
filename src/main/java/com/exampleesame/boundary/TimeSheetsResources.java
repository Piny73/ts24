package com.exampleesame.boundary;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
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

import com.exampleesame.entity.TimeSheet;
import com.exampleesame.store.TimeSheetStore;

/**
 * @author piny73
 */
@Path("timesheet")
@Tag(name = "Gestione TimeSheet", description = "Permette di gestire i TimeSheet")
@DenyAll
public class TimeSheetsResources {
    
    @Inject
    private TimeSheetStore storetimesheet;
    
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce l'elenco di tutti i TimeSheet")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Elenco ritornato con successo"),
        @APIResponse(responseCode = "404", description = "Elenco non trovato")
    })
    @RolesAllowed({"Admin","TimeSheet"})
    public List<TimeSheet> all(@DefaultValue("1") @QueryParam("page") int page, @DefaultValue("10") @QueryParam("size") int size) {
        return storetimesheet.all();
    }
    
    
    /*@GET
    @Path("allslice")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce l'elenco con informazioni ridotte di tutti gli utenti")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "Elenco ritornato con successo"),
        @APIResponse(responseCode = "404", description = "Elenco non trovato")
    })
    @PermitAll
    public JsonArray allSlice() {
        //System.out.println(token);
        return storeuser.all().stream().map(User::toJsonSliceName).collect(JsonCollectors.toJsonArray());
    }*/
    
    
        
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Restituisce il TimeSheet identificato dall'ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "TimeSheet ritornato con successo"),
        @APIResponse(responseCode = "404", description = "TimeSheet non trovato")
    })
    @RolesAllowed({"Admin","TimeSheet"})
    public TimeSheet find(@PathParam("id") Long id) {
        return storetimesheet.find(id).orElseThrow(() -> new NotFoundException("TimeSheet non trovato. id=" + id));
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Permette la registrazione di un nuovo TimeSheet")
    @APIResponses({
        @APIResponse(responseCode = "201", description = "Nuovo TimeSheet creato con successo"),
        @APIResponse(responseCode = "404", description = "Creazione di TimeSheet fallito")
    })
    @PermitAll
    public Response create(@Valid TimeSheet entity) {
        
        if(storetimesheet.findTimeSheetbyProject(entity.getProject()).isPresent()){
            
           return Response.status(Response.Status.PRECONDITION_FAILED).build();
        }
        
        TimeSheet saved = storetimesheet.save(entity);
        
        return Response.status(Response.Status.CREATED)
                .entity(saved)
                .build();
}
    
    @DELETE
    @Path("{id}")
    @Operation(description = "Elimina un TimeSheet tramite l'ID")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "TimeSheet eliminato con successo"),
        @APIResponse(responseCode = "404", description = "TimeSheet non trovato")

    })
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Admin")
    public Response delete(@PathParam("id") Long id) {
        TimeSheet found = storetimesheet.find(id).orElseThrow(() -> new NotFoundException("TimeSheet non trovato. id=" + id));
        storetimesheet.remove(found);
        return Response.status(Response.Status.OK)
                .build();
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description = "Aggiorna i dati del TimeSheet")
    @APIResponses({
        @APIResponse(responseCode = "200", description = "TimeSheet aggirnato con successo"),
        @APIResponse(responseCode = "404", description = "Aggiornamento falito")
            
    })
    @RolesAllowed("Admin")
    public TimeSheet update(@PathParam("id") Long id, @Valid TimeSheet entity) {
        TimeSheet found = storetimesheet.find(id).orElseThrow(() -> new NotFoundException("TimeSheet non trovato. id=" + id));
        entity.setId(id);
        return storetimesheet.update(entity);
    }   
}
