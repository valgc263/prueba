from flask import Flask, jsonify, request
app = Flask(__name__)  # <--- SERVIDOR

animes_list = [
    {
        "id": 3,
        "titulo": "ONE PIECE",
        "rating": 9.3,
        "season": "Fall 1999",
        "generos":  ["Action", "Adventure", "Comedy"],
        "poster": "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/nx21-tXMN3Y20PIL9.jpg",
        },
    {
        "id": 5,
        "titulo": "Shingeki No Kyojin 3 Part 2",
        "rating": 9.8,
        "season": "Spring 2019",
        "generos":  ["Mystery", "Drama", "Action"],
        "poster": "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx104578-LaZYFkmhinfB.jpg",
    },  
]
'''
@app.route("/")
def index():
    return "Hola, Bienvenidos!"


@app.route("/anime", methods="GET")
def lobby_anime():
    return jsonify(animes_list)

@app.route("/anime/<int:id>")
def anime_choice(id):
    []
    return 
'''

@app.route("/")
def index():
    return "Hola, Bienvenidos!"

# localhost:5000/anime ----> PRIMER GET
@app.route('/anime', methods=['GET'])
def lobby_anime():
    return jsonify(animes_list)


# SEGUNDO GET: PARA UN ANIME EN ESPECIFICO
@app.route('/anime/<int:id>')
def anime_id(id):
    anime_found = [anime for anime in animes_list if anime['id'] == id]
    if (len(anime_found) > 0):
        return jsonify({"Anime": anime_found[0]})
    return jsonify({"Message": "Anime no encontrado"})


# POST: PARA AGREGAR UN NUEVO ANIME A LA LISTA
@app.route('/anime', methods =['POST'])
def anime_add():
    data = request.json
    if data:
        anime_nuevo = {
            "id": data.get("id"),
            "titulo": data.get("titulo"),
            "rating": data.get("rating"),
            "seasons": data.get("seasons"),
            "generos": data.get("generos"),
            "poster": data.get("poster"),
        }
        animes_list.append(anime_nuevo)
        return jsonify({'message': 'Anime agregado exitosamente', 'nueva_lista': animes_list})
    else:
        return jsonify({'message': 'Error: Datos de anime no proporcionados'})


    # PATCH: SE REEMPLAZA UN ANIME DE LA LISTA
    @app.route('/anime/<int:id>', methods = ['PUT'])
    def anime_add(anime_id):
        anime_data = request.json
        for anime in animes_list:
            if anime['id'] == anime_id:
                anime.update(anime_data)
                return jsonify({'Mensaje': 'Anime actualizado', 'Nueva lista de animes': animes_list})



if __name__ == "__main__":
    app.run(debug=True, port=5000)


