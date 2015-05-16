.text
.globl main

main:
	la $t0, 0xfffe0000 
	j loop
	
loop:
	addi $t0,$t0,4
	add $t1,$t1,$t0
	sw $t1,($t0)
	j loop
save_all:
	addi $sp, $sp, -4
	sw $t0,($sp)
	addi $sp, $sp, -4
	sw $t1,($sp)
	addi $sp, $sp, -4
	sw $t2,($sp)
	addi $sp, $sp, -4
	sw $t3,($sp)
	addi $sp, $sp, -4
	sw $t4,($sp)
	addi $sp, $sp, -4
	sw $t5,($sp)
	addi $sp, $sp, -4
	sw $t6,($sp)
	addi $sp, $sp, -4
	sw $t7,($sp)
	addi $sp, $sp, -4
	sw $t8,($sp)
	addi $sp, $sp, -4
	sw $t9,($sp)
	jr $31
load_all:
	lw $t9,($sp)
	addi $sp, $sp, 4
	lw $t8,($sp)
	addi $sp, $sp, 4
	lw $t7,($sp)
	addi $sp, $sp, 4
	lw $t6,($sp)
	addi $sp, $sp, 4
	lw $t5,($sp)
	addi $sp, $sp, 4
	lw $t4,($sp)
	addi $sp, $sp, 4
	lw $t3,($sp)
	addi $sp, $sp, 4
	lw $t2,($sp)
	addi $sp, $sp, 4
	lw $t1,($sp)
	addi $sp, $sp, 4
	lw $t0,($sp)
	addi $sp, $sp, 4
	jr $31