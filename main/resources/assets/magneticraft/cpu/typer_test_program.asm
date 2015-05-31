
.text
.globl main

main:
	jal clear_screen
	
	j loop
loop:
	jal read_char_keyboard #set in v0 the key presed or 0 if no key was presed
	addi $a0, $v0,0
	beq $v0,$zero,salto
	jal write_char_monitor #write a char in the monitor
	salto:
	
	j wait
	
read_char_keyboard:
	la $v1, 0xFFFF0000
	lw $v0, ($v1)
	beq $v0, $zero, return_if
	lw $v0, 4($v1)
	sw $zero, ($v1)
	return_if:
	jr $ra
	
write_char_monitor:
	la $k0, 0xffff0008
	lw $k1, ($k0) #carga la posicion del cursor
	beq $a0,8, borrar #si es suprimir se sobrescribe el caracter 
	beq $a0,13, salto_pagina # si es un salto de pagina
	
	add $t0,$k1,$k0	# se pone en $t0 la direccion del caracter
	addi $t0, $t0, 0x0c
	sb $a0, ($t0)	# se escribe el caracter
	addi $k1,$k1, 1
	sw $k1, ($k0)
	jr $ra
	
	borrar:
	beq $k1, $0, back
	li $a0, 32	#se borra el caracter poniendo un spacio
	addi $k1,$k1,-1	# se resta 1 al cursor
	sw $k1, ($k0)	# se guarda el cursor
	add $t0,$k1,$k0	# se pone en $t0 la direccion del caracter
	addi $t0,$t0, 0x0c
	sb $a0, ($t0)	# se escribe el caracter
	jr $ra
	
	salto_pagina:
	addi $k1,$k1, 80 # e suma 80
	addi $t1,$0,80
	div $k1,$t1
	mfhi $t1
	sub $k1,$k1,$t1 # se reata el numero modulo 80 para estar al inicio de linea
	sw $k1, ($k0)
	jr $ra
	
	back:
	jr $ra
	
wait: #halt the computer
	li $v0, 11
	syscall
	j loop
	
clear_screen: #set 4000 bytes to 32
	addi $t0,$0,0
	addi $t1,$0, 4000
	addi $t4,$0, 32
	la $t3, 0xffff0014
	clear:
	addi $t0,$t0,1
	slt $t2,$t0,$t1
	sb $t4, ($t3)
	addi $t3,$t3,1
	bne $t2,$0, clear
	jr $ra
	


	
	
	
