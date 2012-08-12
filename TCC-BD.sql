SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `tcc` DEFAULT CHARACTER SET latin1 ;
USE `tcc` ;

-- -----------------------------------------------------
-- Table `tcc`.`elemento`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`elemento` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `elemento` ENUM('Normal','Fire','Fighting','Water','Flying','Grass','Poison','Electric','Ground','Psychic','Rock','Ice','Bug','Dragon','Ghost','Dark','Steel', ' ') NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `tcc`.`ataque`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`ataque` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NOT NULL ,
  `atk` INT(11) NOT NULL ,
  `elemento` INT NOT NULL ,
  PRIMARY KEY (`id`, `elemento`) ,
  INDEX `fk_ataque_elemento1` (`elemento` ASC) ,
  CONSTRAINT `fk_ataque_elemento1`
    FOREIGN KEY (`elemento` )
    REFERENCES `tcc`.`elemento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tcc`.`pokemon`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`pokemon` (
  `id` INT(11) NOT NULL ,
  `nome` VARCHAR(45) NOT NULL ,
  `raridade` INT(11) NOT NULL ,
  `atkBase` INT(11) NOT NULL ,
  `defBase` INT(11) NOT NULL ,
  `spdBase` INT(11) NOT NULL ,
  `hpBase` INT(11) NOT NULL ,
  `lvlQueEvolui` INT(11) NULL DEFAULT NULL ,
  `idAtaque` INT(11) NOT NULL ,
  `elementoPrimario` INT NOT NULL ,
  `elementoSecundario` INT NULL ,
  PRIMARY KEY (`id`, `idAtaque`, `elementoPrimario`, `elementoSecundario`) ,
  INDEX `fk_pokemon_ataque1` (`idAtaque` ASC) ,
  INDEX `fk_pokemon_elemento1` (`elementoPrimario` ASC) ,
  INDEX `fk_pokemon_elemento2` (`elementoSecundario` ASC) ,
  CONSTRAINT `fk_pokemon_ataque1`
    FOREIGN KEY (`idAtaque` )
    REFERENCES `tcc`.`ataque` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pokemon_elemento1`
    FOREIGN KEY (`elementoPrimario` )
    REFERENCES `tcc`.`elemento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pokemon_elemento2`
    FOREIGN KEY (`elementoSecundario` )
    REFERENCES `tcc`.`elemento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tcc`.`evolucaoporpedra`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`evolucaoporpedra` (
  `id` VARCHAR(45) NOT NULL AUTO_INCREMENT,
  `idPokemon` INT(11) NOT NULL ,
  `idEvolucao` INT(11) NOT NULL ,
  `elemento` INT NOT NULL ,
  PRIMARY KEY (`idPokemon`, `idEvolucao`, `id`, `elemento`) ,
  INDEX `fk_pokemon_has_pokemon_pokemon2` (`idEvolucao` ASC) ,
  INDEX `fk_pokemon_has_pokemon_pokemon1` (`idPokemon` ASC) ,
  INDEX `fk_evolucaoporpedra_elemento1` (`elemento` ASC) ,
  CONSTRAINT `fk_pokemon_has_pokemon_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `tcc`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_pokemon_has_pokemon_pokemon2`
    FOREIGN KEY (`idEvolucao` )
    REFERENCES `tcc`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evolucaoporpedra_elemento1`
    FOREIGN KEY (`elemento` )
    REFERENCES `tcc`.`elemento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tcc`.`jogador`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`jogador` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `nome` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tcc`.`pokemonderrotado`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`pokemonderrotado` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `vezesDerrotado` INT(11) NOT NULL DEFAULT '0' ,
  `idPokemon` INT(11) NOT NULL ,
  PRIMARY KEY (`id`, `idPokemon`) ,
  INDEX `fk_PokemonDerrotado_pokemon1` (`idPokemon` ASC) ,
  CONSTRAINT `fk_PokemonDerrotado_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `tcc`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tcc`.`pokemoninimigo`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`pokemoninimigo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `idPokemon` INT(11) NOT NULL ,
  `tipo` ENUM('boss','minion') NOT NULL ,
  `atk` INT(11) NOT NULL ,
  `def` INT(11) NOT NULL ,
  `spd` INT(11) NOT NULL ,
  `hp` INT(11) NOT NULL ,
  `lvl` INT(11) NOT NULL ,
  PRIMARY KEY (`id`, `idPokemon`) ,
  INDEX `fk_PokemonInimigo_pokemon1` (`idPokemon` ASC) ,
  CONSTRAINT `fk_PokemonInimigo_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `tcc`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tcc`.`pokemonliberado`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`pokemonliberado` (
  `idJogador` INT(11) NOT NULL ,
  `idPokemon` INT(11) NOT NULL ,
  `lvlQueChegou` INT(11) NULL DEFAULT 0 ,
  `faseQueChegou` INT(11) NULL DEFAULT 0 ,
  `inimigosDerrotados` INT(11) NULL DEFAULT 0 ,
  `vezesQueZerouOJogo` INT(11) NULL DEFAULT 0 ,
  `vezesDerrotasParaNPC` INT(11) NULL DEFAULT 0 ,
  `totalDanoCausado` INT(11) NULL DEFAULT 0 ,
  `atk` INT(11) NOT NULL ,
  `def` INT(11) NOT NULL ,
  `spd` INT(11) NOT NULL ,
  `hp` INT(11) NOT NULL ,
  `lvl` INT(11) NULL DEFAULT 1 ,
  `exp` INT NULL DEFAULT 0 ,
  PRIMARY KEY (`idJogador`, `idPokemon`) ,
  INDEX `fk_jogador_has_pokemon_pokemon1` (`idPokemon` ASC) ,
  INDEX `fk_jogador_has_pokemon_jogador` (`idJogador` ASC) ,
  CONSTRAINT `fk_jogador_has_pokemon_jogador`
    FOREIGN KEY (`idJogador` )
    REFERENCES `tcc`.`jogador` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_jogador_has_pokemon_pokemon1`
    FOREIGN KEY (`idPokemon` )
    REFERENCES `tcc`.`pokemon` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `tcc`.`bonusDeElemento`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `tcc`.`bonusDeElemento` (
  `elemento` INT NOT NULL ,
  `elementoVantagem` INT NOT NULL ,
  `elementoFraqueza` INT NOT NULL ,
  PRIMARY KEY (`elemento`, `elementoVantagem`, `elementoFraqueza`) ,
  INDEX `fk_elemento_has_elemento_elemento2` (`elementoVantagem` ASC) ,
  INDEX `fk_elemento_has_elemento_elemento1` (`elementoFraqueza` ASC) ,
  CONSTRAINT `fk_elemento_has_elemento_elemento1`
    FOREIGN KEY (`elementoFraqueza` )
    REFERENCES `tcc`.`elemento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_elemento_has_elemento_elemento2`
    FOREIGN KEY (`elementoVantagem` )
    REFERENCES `tcc`.`elemento` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
