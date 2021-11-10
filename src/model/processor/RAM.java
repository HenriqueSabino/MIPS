/*
 * Projeto - MIPS
 *
 * Grupo:
 *
 * Henrique Sabino
 * Hyan Batista
 * Nelson Lins
 * Silas Augusto
 *
 */

package model.processor;

// Armazena a memória de acesso aleatório do MIPS
public class RAM {

    private byte[] memoryCells;
    private int size;

    public RAM(int bytes) {

        size = bytes;
        memoryCells = new byte[bytes];
    }

    public byte readAt(int position) {

        if (position < size) {
            return memoryCells[position];
        } else {
            throw new IllegalArgumentException("Cannot access memory cell outside memory.");
        }
    }

    public void writeAt(int position, byte value) {

        if (position < size) {
            memoryCells[position] = value;
        } else {
            throw new IllegalArgumentException("Cannot access memory cell outside memory.");
        }
    }

    public int getSize() {
        return size;
    }
}
