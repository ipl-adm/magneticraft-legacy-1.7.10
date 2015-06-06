.include "macros.asm"
.globl main

.text
main:
	jal clearScreen
	li $a0, 0
	jal setCursorPosition
	#jal loadDisk
	jal jumpLine
	
loop:
	jal isKeyPressed
	beq $v0, $zero, loop
	jal resetPressedKeyFlag
	jal getKeyPresed

	beq $v0, 13, jumpL
	beq $v0, 8, suprm
	
	addi $a0, $v0, 0
	jal putChar
	j loop
jumpL:	
	jal jumpLine
	j loop
suprm:
	jal removeChar
	j loop
	jal shutdown # shoutdown the machine
	
loadDisk:
	save_ra
	li $a0,2 # disk drive address
	jal getBufferPointer
	addi $a0, $v0, 0
	addi $s0, $v0, 0
	#li $a1,0
	#jal setSector
	jal loadSerialNumber # change for load sector
waitloop:
	li $a0, 46
	jal putChar
	jal wait
	addi $a0, $s0, 0
	jal isBufferReady
	beq $v0, $zero, waitloop
	li $a0, 47
	jal putChar
	addi $a0, $s0, 0
	jal printString
	load_ra
	return
	
.include "computer_driver.asm"
.include "disk_driver.asm"
.include "monitor_driver.asm"
