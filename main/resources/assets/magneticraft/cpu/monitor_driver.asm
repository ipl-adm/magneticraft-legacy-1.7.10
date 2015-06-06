
#this script requires macros.asm
.text

isKeyPressed:# return 1 if a key was pressed
	la $t0, 0xFF010000
	lw $t1, ($t0)
	andi $v0, $t1, 1
	return
	
resetPressedKeyFlag:# restet the keyboard to listen again for a key press
	la $t0, 0xFF010000
	li $t1, 0
	sw $t1, ($t0)
	return
	
getKeyPresed:# return the las pressed key
	la $t0, 0xFF010004
	lw $v0, ($t0)
	return
	
getCursorPosition:# return the position of the cursor  
	la $t0, 0xFF010008
	lw $v0, ($t0)
	return
	
setCursorPosition:# args: a0 new position for the cursor
	la $t0, 0xFF010008
	sw $a0, ($t0)
	return
	
setChar:# args: a0 char, a1 position in [0, 4000)
	la $t0, 0xFF010014
	add $t0, $a1, $t0
	sb $a0, ($t0)
	return
	
getChar:# args: a0 position in [0, 4000)
	la $t0, 0xFF010014
	add $t0, $a0, $t0
	lb $v0, ($t0)
	return

clearScreen:
	li $s0, 4000
	li $a0, 32
	li $a1, 0
loop1:	
	save_ra
	jal setChar
	load_ra
	addi $s0, $s0, -1
	addi $a1, $a1, 1
	bne $s0, $zero, loop1 
	return

putChar:#args: a0 character
	save_ra
	jal getCursorPosition
	addi $a1 $v0, 0
	jal setChar
	addi $a0, $a1, 1
	jal setCursorPosition
	load_ra
	return
	
removeChar:
	save_ra
	jal getCursorPosition
	li $a0, 32
	addi $a1 $v0, -1
	jal setChar
	addi $a0, $a1, 0
	jal setCursorPosition
	load_ra
	return

printString:#args: a0 string pointer
	save_ra
	save_s0
	addi $s0, $a0, 0
loopStr:
	lb $a0, ($s0)
	beq $zero, $a0, exitLoop
	addi $s0, $s0, 1
	jal putChar
	j loopStr
exitLoop:
	load_s0
	load_ra
	return

jumpLine:
	save_ra
	jal getCursorPosition
	li $t1, 80
	div $v0, $t1
	mfhi $t0
	sub $a0, $v0, $t0
	addi $a0, $a0, 80
	jal setCursorPosition
	load_ra
	return
	
printInt:#args: a0 int to print
	save_ra
	save_s0
	li $t0, 10
	addi $s0, $a0, 0
intloop:
	divu  $s0, $t0
	mfhi $a0
	mflo $s0
	addi $a0 $a0, 48
	jal putChar
	bne $s0, $zero, intloop
	load_so
	load_ra
	return

