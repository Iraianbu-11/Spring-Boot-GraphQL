package com.iraianbu.graphql.service;

import com.iraianbu.graphql.model.Player;
import com.iraianbu.graphql.model.Team;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlayerService {

    private final List<Player> players = new ArrayList<Player>();

    AtomicInteger id = new AtomicInteger(0);

    public List<Player> findAll() {
        return players;
    }

    public Optional<Player> findOne(Integer id){
        return players.stream()
                .filter(player -> Objects.equals(player.Id(), id))
                .findFirst();
    }

    public Player create(String name , Team team){
        Player player = new Player(id.incrementAndGet(), name, team);
        players.add(player);
        return player;
    }

    public Player delete(Integer id){
       Player player = players.stream()
               .filter(c-> Objects.equals(c.Id(), id))
               .findAny().orElseThrow( ()-> new RuntimeException("Player not found"));
       players.remove(player);
       return player;
    }

    public Player update(Integer id , String name , Team team){
        Player updatedPlayer = new Player(id,name,team);
        Optional<Player> optional = players.stream()
                .filter(c-> Objects.equals(c.Id(), id)).findFirst();
        if(optional.isPresent()){
            Player player = optional.get();
            int index = players.indexOf(player);
            players.set(index, updatedPlayer);
        }
        else{
            throw new RuntimeException("Player not found");
        }
        return updatedPlayer;
    }

    @PostConstruct
    private void init(){
        players.add(new Player(id.incrementAndGet(),"MS Dhoni",Team.CSK));
        players.add(new Player(id.incrementAndGet(),"Virat Kohli",Team.RCB));
        players.add(new Player(id.incrementAndGet(),"Rohit Sharma",Team.MI));
        players.add(new Player(id.incrementAndGet(),"Sunil Narine",Team.KKR));
        players.add(new Player(id.incrementAndGet(),"Ravindra Jadeja",Team.CSK));
    }
}
