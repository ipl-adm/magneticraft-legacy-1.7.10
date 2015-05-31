
shutdown:
	li $t0,1
	sb $t0, 1($zero)
	return

wait:
	li $t0,1
	sb $t0, 3($zero)
	return
	
getTime:
	li $t0, 0x0
	lw $v0, 4($t0)
	return
