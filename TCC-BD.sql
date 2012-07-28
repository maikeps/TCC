SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `TCC` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `TCC` ;

-- -----------------------------------------------------
-- Table `TCC`.`jogador`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TCC`.`jogador` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TCC`.`ataque`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TCC`.`ataque` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NOT NULL ,
  `atk` INT NOT NULL ,
  `elemento` ENUM('fire', '...') NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TCC`.`pokemon`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TCC`.`pokemon` (
  `id` INT NOT NULL ,
  `nome` VARCHAR(45) NOT NULL ,
  `raridade` INT NOT NULL ,
  `atkBase` INT NOT NULL ,
  `defBase` INT NOT NULL ,
  `spdBase` INT NOT NULL ,
  `hpBase` INT NOT NULL ,
  `lvlQueEvolui` INT NULL ,
  `idAtaque` INT NOT NULL ,
  `elementoPrimario` ENUM('fire', '...') NOT NULL ,
  `elementoSecundario` ENUM('fire', '...') NULL ,
  PRIMARY KEY (`id`, `idAtaque`) ,
  INDEX `fk_pokemon_ataque1` (`idAtaque` ASC) ,
  CONSTRAINT `fk_pokemon_ataque1`
    FOREIGN KEY (`idAtaque` )
    REFERENCES `TCC`.`ataque` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TCC`.`pokemonLiberado`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TCC`.`pokemonLiberado` (
  `idJogador` INT NOT NULL ,
  `idPokemon` INT NOT NULL ,
  `lvlQueChegou` INT NOT NULL ,
  `faseQueChegou` INT NOT NULL ,
  `inimigosDerrotados` INT NOT NULL ,
  `vezesQueZerouOJogo` INT NOT NULL ,
  `vezesDerrotasParaNPC` INT NOT NULL ,
  `totalDanoCausado` INT NOT NULL ,
  `medalhas` INT NOT NULL ,
  `atk` INT NOT NULL ,
  `def` INT NOT NULL ,
  `spd` INT NOT NULL ,
  `lvl` INT NOT NULL ,
  `hp` INT NOT NULL ,
  PRIMARY KEY (`idJogador`, `idPokemon`) ,
  INDEX `fk_jogador_has_pokemon_pokemon1` (`idPokemon` ASC) ,
  INDEX `fk_jogador_has_pokemon_jogador` (`idJogador` ASC) ,
  CONSTRAINT `fk_jogador_has_pokemon_jogador`
    FOREIGN KEY (`idJogador` )
    REFERENCES `TCC`.`jogador` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jogador_has_pokemon_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `TCC`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TCC`.`PokemonDerrotado`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TCC`.`PokemonDerrotado` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `vezesDerrotado` INT NOT NULL DEFAULT 0 ,
  `idPokemon` INT NOT NULL ,
  PRIMARY KEY (`id`, `idPokemon`) ,
  INDEX `fk_PokemonDerrotado_pokemon1` (`idPokemon` ASC) ,
  CONSTRAINT `fk_PokemonDerrotado_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `TCC`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TCC`.`PokemonInimigo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TCC`.`PokemonInimigo` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `idPokemon` INT NOT NULL ,
  `tipo` ENUM('boss', 'minion') NOT NULL ,
  `atk` INT NOT NULL ,
  `def` INT NOT NULL ,
  `spd` INT NOT NULL ,
  `hp` INT NOT NULL ,
  `lvl` INT NOT NULL ,
  PRIMARY KEY (`id`, `idPokemon`) ,
  INDEX `fk_PokemonInimigo_pokemon1` (`idPokemon` ASC) ,
  CONSTRAINT `fk_PokemonInimigo_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `TCC`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TCC`.`evolucaoPorPedra`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `TCC`.`evolucaoPorPedra` (
  `id` VARCHAR(45) NOT NULL ,
  `idPokemon` INT NOT NULL ,
  `idEvolucao` INT NOT NULL ,
  `pedra` ENUM('fire', 'thunder', '...') NOT NULL ,
  PRIMARY KEY (`idPokemon`, `idEvolucao`, `id`) ,
  INDEX `fk_pokemon_has_pokemon_pokemon2` (`idEvolucao` ASC) ,
  INDEX `fk_pokemon_has_pokemon_pokemon1` (`idPokemon` ASC) ,
  CONSTRAINT `fk_pokemon_has_pokemon_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `TCC`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pokemon_has_pokemon_pokemon2`
    FOREIGN KEY (`idEvolucao` )
    REFERENCES `TCC`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
