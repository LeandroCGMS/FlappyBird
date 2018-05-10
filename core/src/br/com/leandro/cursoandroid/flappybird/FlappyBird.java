package br.com.leandro.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    private SpriteBatch batch;
    private Texture[] passaros;
    private int movimento = 0;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Texture gameOver;
    private Random numeroRandomico;
    private BitmapFont fonte;
    private BitmapFont mensagem;
    private Circle passaroCirculo;
    private Rectangle retanguloCanoTopo;
    private Rectangle retanguloCanoBaixo;
    //private ShapeRenderer shape;

    //Atributos de configuração
    private Texture fundo;
    private int larguraDispositivo;
    private int alturaDispositivo;
    private int estadoJogo = 0;//0 -> jogo não iniciado e 1 -> jogo iniciado e 2 -> Tela Game Over
    private int pontuacao = 0;

    private float variacao = 0;
    private float velocidadeQueda = 0;
    private float posicaoInicialVertical;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoEntreCanos;
    private float deltaTime;
    private float alturaEntreCanosRandomica;
    private boolean marcouPonto;


    @Override
    public void create() {

        batch = new SpriteBatch();
        numeroRandomico = new Random();
        passaroCirculo = new Circle();
/*        retanguloCanoTopo = new Rectangle();
          retanguloCanoBaixo = new Rectangle();
        shape = new ShapeRenderer();*/
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
        fonte.getData().setScale(6);

        mensagem = new BitmapFont();
        mensagem.setColor(Color.WHITE);
        mensagem.getData().setScale(3);

        passaros = new Texture[3];
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");

        fundo = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo = new Texture("cano_topo.png");
        gameOver = new Texture("game_over.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaDispositivo / 2;
        posicaoMovimentoCanoHorizontal = larguraDispositivo - 100;
        espacoEntreCanos = 300;

    }

    @Override
    public void render() {

        deltaTime = Gdx.graphics.getDeltaTime();
        variacao += deltaTime * 10;
        if (variacao > 2) variacao = 0;


        if (estadoJogo == 0) {//Não iniciado
            if (Gdx.input.justTouched()) {
                estadoJogo = 1;
            }
        } else {//Iniciado

            velocidadeQueda++;
            if (posicaoInicialVertical > 0 || velocidadeQueda < 0)
                posicaoInicialVertical -= velocidadeQueda;

            if (estadoJogo == 1) {
                posicaoMovimentoCanoHorizontal -= deltaTime * 200;
                if (Gdx.input.justTouched()) {
                    velocidadeQueda = -15;
                }

                // Verifica se o cano saiu inteiramente da tela
                if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                    alturaEntreCanosRandomica = numeroRandomico.nextInt((400) - 200);
                    marcouPonto = false;
                }

                //Verifica pontuação
                if (posicaoMovimentoCanoHorizontal < 120) {
                    if (!marcouPonto) {
                        pontuacao++;
                        marcouPonto = true;
                    }
                }

            } else {//Tela Game Over -> 2

                if (Gdx.input.justTouched()) {
                    estadoJogo = 0;
                    pontuacao = 0;
                    velocidadeQueda = 0;
                    posicaoInicialVertical = alturaDispositivo / 2;
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                }

            }


        }
        batch.begin();

        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2
                + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, alturaDispositivo / 2
                - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica);
        batch.draw(passaros[(int) variacao], 120, posicaoInicialVertical);

        fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo / 2,
                alturaDispositivo - 50);

        if (estadoJogo == 2) {
            batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth() / 2, alturaDispositivo / 2);
            mensagem.draw(batch, "Toque para Reiniciar.", larguraDispositivo / 2 - 200, alturaDispositivo / 2 - gameOver.getHeight() / 2);
        }

        batch.end();

        passaroCirculo.set(120 + passaros[0].getWidth() / 2,
                posicaoInicialVertical + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);
        retanguloCanoBaixo = new Rectangle(
                posicaoMovimentoCanoHorizontal,
                alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 +
                        alturaEntreCanosRandomica,
                canoBaixo.getWidth(),
                canoBaixo.getHeight()
        );

        retanguloCanoTopo = new Rectangle(
                posicaoMovimentoCanoHorizontal,
                alturaDispositivo / 2
                        + espacoEntreCanos / 2 + alturaEntreCanosRandomica,
                canoTopo.getWidth(), canoTopo.getHeight()
        );

        //Desenhar formas
        /*shape.begin( ShapeRenderer.ShapeType.Filled );
        shape.circle( passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius );
        shape.rect(retanguloCanoBaixo.x,retanguloCanoBaixo.y,retanguloCanoBaixo.width,
                retanguloCanoBaixo.height);
        shape.rect(retanguloCanoTopo.x, retanguloCanoTopo.y, retanguloCanoTopo.width,
                retanguloCanoTopo.height);
        shape.setColor(Color.RED);
        shape.end();*/

        //Teste de colisão
        if (Intersector.overlaps(passaroCirculo, retanguloCanoBaixo)
                || Intersector.overlaps(passaroCirculo, retanguloCanoTopo)
                || posicaoInicialVertical <= 0 || posicaoInicialVertical >= alturaDispositivo) {
            estadoJogo = 2;
        }

    }


}
