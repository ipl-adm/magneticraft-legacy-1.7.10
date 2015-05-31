
#buffer always has 128 byte of size.

getBufferPointer:#args: a0 disk address
	la $t0, 0xFF000000
	sll $t1, $a0, 15
	or $v0, $t0, $t1
	return
	
isBufferReady:#args: a0 buffer pointer, return 1 is buffer is loaded 0 otherwise.
	lb $t0, 130($a0)
	la $t1, 0xF
	andi $t0, $t0, 0xF
	beq $t0, $t1, return_true
	li $v0, 0
	return
return_true:
	li $v0, 1
	return	
	
loadDiskName:#load the name of the disk in the buffer, args: a0 buffer pointer
	li $t0, 1
	sb $t0, 130($a0)
	return

saveDiskName:#args: a0 buffer pointer
	li $t0, 2
	sb $t0, 130($a0)
	return
	
loadSerialNumber:#args: a0 buffer pointer
	li $t0, 3
	sb $t0, 130($a0)
	return
	
loadSector:#args: a0 buffer pointer
	li $t0, 4
	sb $t0, 130($a0)
	return
	
saveSector:#args: a0 buffer pointer
	li $t0, 5
	sb $t0, 130($a0)
	return

setSector:#args: a0 buffer pointer, a1 the new sector
	sb $a1, 128($a0)
	srl $a1, $a1, 8
	sb $a1, 129($a0)
	return

getSector:#args: a0 buffer pointer
	lb $t0, 128($a0)
	lb $t1, 129($a0)
	sll $v0, $t1, 8
	or $v0, $v0, $t0
	return
	
