package br.com.leandro.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture passaro;
    private int movimento = 0;

    //Atributos de configuração
    private Texture fundo;
    private int larguraDispositivo;
    private int alturaDispositivo;


    @Override
    public void create() {

        batch = new SpriteBatch();
        passaro = new Texture("passaro1.png");
        fundo = new Texture("fundo.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();

    }

    @Override
    public void render() {
        movimento += 4;

        batch.begin();

        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(passaro, movimento, 400);

        batch.end();

    }


}
