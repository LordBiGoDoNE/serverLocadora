package br.com.rafaelsoftworks.aula;

import br.com.rafaelsoftworks.aula.model.entity.Carro;
import br.com.rafaelsoftworks.aula.model.entity.Fabricante;
import br.com.rafaelsoftworks.aula.model.entity.Modelo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class LocadoraApplicationTests {

    @Test
    void threadTeste() throws InterruptedException {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

//        list.stream().filter(n -> n % 2 == 0).forEach(System.out::println);
//
//        for (Integer numero : list) {
//            if (numero % 2 == 0) {
//                System.out.println(numero);
//            }
//        }

        List<Fabricante> fabricantes = List.of(
                new Fabricante(1, "Rafael"),
                new Fabricante(2, "Vieira"),
                new Fabricante(3, "Silva"),
                new Fabricante(4, "NAO CADASTRADO NENHUM MODELO"),
                new Fabricante(5, "URUBU")
        );

        List<Integer> idsFabricantes = new ArrayList<>();

        for (Fabricante fabricante : fabricantes) {
            idsFabricantes.add(fabricante.getId());
        }

        List<String> idsFabricantesLambda = fabricantes.stream().map(Fabricante::getNome).filter(n -> n.equals("Vieira")).toList();

        Modelo fox = new Modelo(1, "FOX", fabricantes.get(0));
        Modelo gol = new Modelo(2, "GOL", fabricantes.get(1));
        Modelo passat = new Modelo(3, "PASSAT", fabricantes.get(2));
        Modelo versa = new Modelo(4, "VERSA", fabricantes.get(4));

        List<Modelo> modelos = List.of(fox, gol, passat, versa);

        Carro carro1 = new Carro(1, "FOX 1234ABC", fox);
        Carro carro2 = new Carro(2, "GOL 1234ABC", gol);
        Carro carro3 = new Carro(3, "PASSAT 1234ABC", passat);

        List<Carro> carros = List.of(carro1, carro2, carro3);

        List<String> nomeFabricantes = new ArrayList<>();

        carros.forEach(carro -> {
            if (carro.getModelo().getId() % 2 == 1) {
                nomeFabricantes.add(carro.getModelo().getFabricante().getNome());
            }
        });

        List<String> nomeFabricantesStream = new ArrayList<>();

        carros.stream()
                .map(Carro::getModelo)
                .map(Modelo::getFabricante)
                .filter(modelo -> modelo.getId() % 2 == 1)
                .map(Fabricante::getNome)
                .forEach(nomeFabricantesStream::add);

        carros.forEach(carro -> {
                nomeFabricantes.add(carro.getModelo().getFabricante().getNome());
        });

        carros.stream()
                .map(carro -> carro.getModelo().getFabricante().getNome())
                .forEach(nomeFabricantesStream::add);

        if (modelos.stream().anyMatch(modelo -> modelo.getFabricante().getNome().contains("URUBU"))) {
            System.out.println("CONTEM");
        }

        nomeFabricantes.forEach(System.out::println);
        nomeFabricantesStream.forEach(System.out::println);
    }
}

