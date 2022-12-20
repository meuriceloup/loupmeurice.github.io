
class Controller {

    infoMessageDiv = document.getElementById("info");
    bestPlay = null;

    constructor() {
        this.model = new Model();
        this.drawTiles();
        this.refreshScorePanel();
    }

    searchForBestPlay() {
        this.displayInfoMessage("Le maître du jeu est entrain de réfléchir. Veuillez patienter.");
        setTimeout(() => {
           this.bestPlay = master.getBestPlay();
            this.displayInfoMessage("À vous de jouer !");
        }, 1);
    }

    refreshScorePanel() {
        document.getElementById("yourscore").innerHTML = this.model.myScore + "" + (this.model.myLastPlay != null ? " (+" + this.model.myLastPlay + ")" : "" );
        document.getElementById("masterscore").innerHTML = this.model.masterScore + "" + (this.model.masterLastPlay != null ? " (+" + this.model.masterLastPlay + ")" : "" );
        document.getElementById("playnumber").innerHTML = this.model.numberOfPlays + "";
        document.getElementById("letternumber").innerHTML = this.model.getNumberOfRemainingLetters() + "";
    }

    displayInfoMessage(msg) {
        this.infoMessageDiv.classList.remove("transparent");
        this.infoMessageDiv.innerText = msg;
    }

    removeInfoMessage() {
        this.infoMessageDiv.classList.add("transparent");
        //this.infoMessageDiv.innerText = "";
    }

    addTileToBoard(i, j, l) {
        this.model.add(i, j, l);
    }

    removeTileFromBoard(i, j) {
        this.model.remove(i, j);
    }

    removeAllTilesFromBoard() {
        this.model.removeAllTilesFromBoard();
    }

    play() {
        let error = this.isValid();
        if(!error) {
            let cnt = this.getPoints();
            this.setError("");
            this.masterPlay(cnt, this.bestPlay);
            return cnt;
        } else {
            this.setError(error);
        }

        //if(error)
        //    console.log(error);
        return -1;
    }

    masterPlay(myPlay, masterPlay) {
        this.model.addPlay(myPlay, masterPlay);
        this.refreshScorePanel();
        resetRackAndBoard();
        this.removeAllTilesFromBoard();
        let word = masterPlay.word;
        let place = masterPlay.place;
        for(let i = 0; i < place.length; i++) {
            this.model.board[place[i].row][place[i].column] = word.charAt(i);
        }

        this.removeLettersFromRack(word);
        this.drawTiles();
        this.searchForBestPlay();

    }

    removeLettersFromRack(word) {
        for(const char of word) {
            for(let i = 0; i < this.model.rack.length; i++) {
                if(this.model.rack[i] == char) {
                    this.model.rack.splice(i, 1);
                    i--;
                    break;
                }
            }
        }
    }

    setError(error) {
        document.getElementById("error").innerHTML = error;
    }

    isValid() {

        let rack = [...this.model.rack];
        let play = [...this.model.getPlayedLetters()];
        for(const letter of play) {
            const index = rack.indexOf(letter);
            if(index == -1)
                return "Vous ne disposez des letters nécessaires pour effectuer ce coup"
            rack.splice(index, 1);
        }


        let rows = this.model.getPlayedRows();
        let columns = this.model.getPlayedColumns();
        if(rows.length == 0 && columns.length == 0) {
            return "Veuillez former un mot avec vos tuiles"
        }

        if(rows.length == 1 && columns.length == 1 && this.model.isEmptyBoard())
            return "Deux tuiles minimum doivent être posées lors du premier coup";
        let ok = false;
        let firstPlay = this.model.isEmptyBoard();

        if(rows.length == 1 && columns.length >= 1) {
            if(firstPlay) {
                //first play
                if(rows[0] != this.model.center[0])
                    return "Le premier mot doit obligatoirement être posé au centre du plateau, sur l’étoile";
                if(!columns.includes(this.model.center[1]))
                    return "Le premier mot doit obligatoirement être posé au centre du plateau, sur l’étoile";

            }

            for(let i = 0; i < columns.length; i++)
                if(this.model.board[rows[0]][columns[i]] != null)
                    return "Vos tuiles sont posées sur une case non-libre"; //the tile is not free

            ok = true;
        } else {
            if(rows.length >= 1 && columns.length == 1) {
                if(firstPlay) {
                    //first play
                    if(columns[0] != this.model.center[1])
                        return "Le premier mot doit obligatoirement être posé au centre du plateau, sur l’étoile";
                    if(!rows.includes(this.model.center[0]))
                        return "Le premier mot doit obligatoirement être posé au centre du plateau, sur l’étoile";
                }

                for(let i = 0; i < rows.length; i++)
                    if(this.model.board[rows[i]][columns[0]] != null)
                        return "Vos tuiles sont posées sur une case non-libre"; //the tile is not free
                ok = true;
            }
        }

        if(ok) {
            //conditions respected at this point:
            //1) only one row or column filled
            //2) center filled if first play
            //3) the played tiles are free

            if(!firstPlay) {
                if(!this.isAttached()) {
                    return "Au moins une tuile doit être attachée à un mot existant";
                }
            }

            if(this.getGaps().length > 0)
                return "Il existe des cases libres entre vos tuiles posées"; //some gaps between the played tiles
            else {
                //no gaps
                let words = this.getCreatedWords();
                for(let i = 0; i < words.length; i++) {
                    if(!this.isValidWord(words[i].word)) {
                        return "Le mot '" + words[i].word.toUpperCase() + "' n'existe pas";
                    }
                }

                return null;

            }
        }


        return "Les mots doivent toujours être écrits de gauche à droite ou de haut en bas";


    }

    isValidWord(word) {
        return dictionary.has(word.toUpperCase());
    }

    isAttached() {
        let playedColumns = this.model.getPlayedColumns();
        let playedRows = this.model.getPlayedRows();

        if (playedRows.length == 1 && playedColumns.length >= 1) {
            //horizontal word
            let row_num = playedRows[0];
            for (let i = 0; i < playedColumns.length; i++) {
                let col_num = playedColumns[i];

                if (col_num >= 1) {
                    //check left
                    if (this.model.board[row_num][col_num - 1] != null)
                        return true;
                }

                if (col_num < this.model.column_length - 1) {
                    //check right
                    if (this.model.board[row_num][col_num + 1] != null)
                        return true;
                }

                if (row_num >= 1) {
                    //check top
                    if (this.model.board[row_num - 1][col_num] != null)
                        return true;
                }

                if (row_num < this.model.row_length - 1) {
                    //check bottom
                    if (this.model.board[row_num + 1][col_num] != null)
                        return true;
                }


            }
        } else {
            //vertical word
            let col_num = playedColumns[0];
            for (let i = 0; i < playedRows.length; i++) {
                let row_num = playedRows[i];

                if (row_num >= 1) {
                    //check top
                    if (this.model.board[row_num - 1][col_num] != null)
                        return true;
                }

                if (row_num < this.model.row_length - 1) {
                    //check bottom
                    if (this.model.board[row_num + 1][col_num] != null)
                        return true;
                }

                if (col_num >= 1) {
                    //check left
                    if (this.model.board[row_num][col_num - 1] != null)
                        return true;
                }

                if (col_num < this.model.column_length - 1) {
                    //check right
                    if (this.model.board[row_num][col_num + 1] != null)
                        return true;
                }
            }
        }
        return false;
    }


    getCreatedWords() {
        let words = [];
        let playedColumns = this.model.getPlayedColumns();
        let playedRows = this.model.getPlayedRows();

        if(playedRows.length == 1) {
            //row
            let start = playedColumns[0];
            let end = playedColumns[playedColumns.length - 1];
            let word = "";
            let letters = [];
            for(let i = start; i <= end; i++) {
                if(playedColumns.includes(i)) {
                    word += this.model.play[playedRows[0]][i];
                    letters.push(
                        {'letter': this.model.play[playedRows[0]][i],
                        'coordinates' : [playedRows[0],i],
                        'rack' : 'true'}
                        )
                }
                if(this.model.board[playedRows[0]][i] != null) {
                    word += this.model.board[playedRows[0]][i];
                    letters.push(
                        {'letter': this.model.board[playedRows[0]][i],
                            'coordinates' : [playedRows[0],i],
                            'rack' : 'false'}
                            );
                }
            }

            for(let i = start - 1; i >= 0; i--) {
                //left
                if(this.model.board[playedRows[0]][i] != null) {
                    word = this.model.board[playedRows[0]][i] + word;
                    letters.unshift(
                        {'letter': this.model.board[playedRows[0]][i],
                            'coordinates' : [playedRows[0],i],
                            'rack' : 'false'});
                }
                else
                    break;
            }

            for(let i = end + 1; i < this.model.column_length; i++) {
                //right
                if(this.model.board[playedRows[0]][i] != null) {
                    word = word + this.model.board[playedRows[0]][i];
                    letters.push(
                        {'letter': this.model.board[playedRows[0]][i],
                            'coordinates' : [playedRows[0],i],
                            'rack' : 'false'}
                    );
                }
                else
                    break;
            }

            if(word.length > 1)
                words.push({'word' : word, 'letters' : letters});

            //vertical words
            for(let i = 0; i < playedColumns.length; i++) {
                let word = this.model.play[playedRows[0]][playedColumns[i]];
                let letters = [];
                letters.push(
                    {'letter': word,
                        'coordinates' : [playedRows[0],playedColumns[i]],
                        'rack' : 'true'}
                );
                for(let j = playedRows[0] - 1; j > 0; j--) {
                    if(this.model.board[j][playedColumns[i]] != null) {
                        word = this.model.board[j][playedColumns[i]] + word;
                        letters.unshift(
                            {'letter': this.model.board[j][playedColumns[i]],
                                'coordinates' : [j,playedColumns[i]],
                                'rack' : 'false'});
                    }
                    else
                        break;
                }

                for(let j = playedRows[0] + 1; j < this.model.row_length; j++) {
                    if(this.model.board[j][playedColumns[i]] != null) {
                        word += this.model.board[j][playedColumns[i]];
                        letters.push(
                            {'letter': this.model.board[j][playedColumns[i]],
                                'coordinates' : [j,playedColumns[i]],
                                'rack' : 'false'}
                        );
                    }
                    else
                        break;
                }
                if(word.length > 1)
                    words.push({'word' : word, 'letters' : letters});
            }


        } else {
            //columns
            let start = playedRows[0];
            let end = playedRows[playedRows.length - 1];
            let word = "";
            let letters = [];
            for(let i = start; i <= end; i++) {
                if (playedRows.includes(i)) {
                    word += this.model.play[i][playedColumns[0]];
                    letters.push(
                        {'letter': this.model.play[i][playedColumns[0]],
                            'coordinates' : [i,playedColumns[0]],
                            'rack' : 'true'}
                    );
                }
                if (this.model.board[i][playedColumns[0]] != null) {
                    word += this.model.board[i][playedColumns[0]]
                    letters.push(
                        {'letter': this.model.board[i][playedColumns[0]],
                            'coordinates' : [i,playedColumns[0]],
                            'rack' : 'false'}
                    );
                }
            }

            for(let i = start - 1; i >= 0; i--) {
                //bottom
                if(this.model.board[i][playedColumns[0]] != null) {
                    word = this.model.board[i][playedColumns[0]] + word;
                    letters.unshift(
                        {'letter': this.model.board[i][playedColumns[0]],
                            'coordinates' : [i,playedColumns[0]],
                            'rack' : 'false'});
                }
                else
                    break;
            }

            for(let i = end + 1; i < this.model.row_length; i++) {
                //top
                if(this.model.board[i][playedColumns[0]] != null) {
                    word = word + this.model.board[i][playedColumns[0]];
                    letters.push(
                        {'letter': this.model.board[i][playedColumns[0]],
                            'coordinates' : [i,playedColumns[0]],
                            'rack' : 'false'});
                }
                else
                    break;
            }

            if(word.length > 1)
                words.push({'word' : word, 'letters' : letters});

            //horizontal words
            for(let i = 0; i < playedRows.length; i++) {
                let word = this.model.play[playedRows[i]][playedColumns[0]];
                let letters = [];
                letters.push(
                    {'letter': word,
                        'coordinates' : [playedRows[i],playedColumns[0]],
                        'rack' : 'true'}
                );
                for(let j = playedColumns[0] - 1; j > 0; j--) {
                    if(this.model.board[playedRows[i]][j] != null) {
                        word = this.model.board[playedRows[i]][j] + word;
                        letters.unshift(
                            {'letter': this.model.board[playedRows[i]][j],
                                'coordinates' : [playedRows[i],j],
                                'rack' : 'false'});
                    }
                    else
                        break;
                }

                for(let j = playedColumns[0] + 1; j < this.model.row_length; j++) {
                    if(this.model.board[playedRows[i]][j] != null) {
                        word += this.model.board[playedRows[i]][j];
                        letters.push(
                            {'letter': this.model.board[playedRows[i]][j],
                                'coordinates' : [playedRows[i],j],
                                'rack' : 'false'});
                    }
                    else
                        break;
                }
                if(word.length > 1)
                    words.push({'word' : word, 'letters' : letters});
            }
        }

        return words;
    }

    getGaps() {
        let res = [];

        let playedColumns = this.model.getPlayedColumns();
        let playedRows = this.model.getPlayedRows();

        if(playedRows.length == 1) {
            //row
            let start = playedColumns[0];
            let end = playedColumns[playedColumns.length - 1];
            for(let i = start + 1; i < end; i++) {
                if(!playedColumns.includes(i) && this.model.board[playedRows[0]][i] == null)
                    res.push([playedRows[0], i])
            }

        } else {
            //columns
            let start = playedRows[0];
            let end = playedRows[playedRows.length - 1];
            for(let i = start + 1; i < end; i++)
                if(!playedRows.includes(i) && this.model.board[i][playedColumns[0]] == null)
                    res.push([i, playedColumns[0]])
        }

        return res;
    }

    drawTiles() {
        for(let i = this.model.rack.length; i < this.model.nb_of_rack_riles; i++) {
            let index = Math.floor(Math.random() * this.model.tiles.length);
            this.model.rack.push(this.model.tiles[index]);
            this.model.tiles.splice(index, 1);
        }

        this.refreshView();
    }

    refreshView() {
        //refreshing rack
        let rack = document.querySelector(".rack");
        for(let i = 0; i < this.model.nb_of_rack_riles; i++) {
            let tile = rack.querySelector(".draggable[position='" + i + "'] .playable-tile");
            tile.setAttribute("data-letter", this.model.rack[i].toLowerCase());
        }

        //refreshing board
        let board = document.getElementById("js-board");
        for(let i = 0; i < this.model.row_length; i++)
            for(let j = 0; j < this.model.column_length; j++) {
                if(this.model.board[i][j] != null) {
                    let tile = board.querySelector(".tile[data-row='" + i + "'][data-col='" + j + "'] .playable-tile");
                    tile.setAttribute("data-letter", this.model.board[i][j])
                    tile.removeAttribute("position");
                    tile.parentNode.classList.remove("invisible");
                    tile.parentNode.parentNode.setAttribute("free", "false");
                }
            }
    }

    getPoints() {
        let words = this.getCreatedWords();
        let total = 0;
        for(const word of words) {
            let count = 0;
            let factor = 1;
            for(const letter of word.letters) {
                let points = this.model.getLetterPoint(letter['letter']);
                if(letter['rack'] == 'true') {
                    let bonus = this.model.getBonus(letter['coordinates']);
                    if(bonus != null) {
                        switch (bonus) {
                            case MT:
                                factor = factor * 3;
                                break;
                            case MD:
                                factor = factor * 2;
                                break;
                            case LD:
                                points = points * 2;
                                break;
                            case LT:
                                points = points * 3;
                                break;
                        }
                    }

                }
                count += points;
            }
            total += (count * factor);
        }

        let nbOfLetters = 0;
        for(const row of this.model.play)
            for(const cell of row)
                if(cell != null)
                    nbOfLetters++;
        if(nbOfLetters == SCRABBLE_LENGTH)
            total += SCRABBLE_PRIME;
        return total;
    }



}


