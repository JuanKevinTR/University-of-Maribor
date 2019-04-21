#------------------------------
# @author J. Kevin Trujillo
# @version Ubuntu 18.04.2 LTS x86_64
# web: juankevintrujillo.com
#------------------------------

function convert(){
	sudo mkfs.ext4 -F $partitionToConvert
	echo -e "\t==> Convert function finished!"
}

function create(){
	echo -e "Disk to be partitioned -> $diskToParted"

	first="1"
	firstPartition="$diskToParted$first" # e.g. /dev/sdb1
	second="2"
	secondPartition="$diskToParted$second" # e.g. /dev/sdb2

	#echo -e "\n$firstPartition - $secondPartition\n"

	sudo umount $diskToParted
	sudo umount /mnt$firstPartition
	sudo umount /mnt$secondPartition

	sudo parted -s $diskToParted mklabel gpt

	sudo parted -s $diskToParted mkpart primary $FileSystemType 0% 50%
	sudo parted -s $diskToParted name 1 '1-Partition'
	sudo parted -s $diskToParted mkpart primary $FileSystemType 50% 100%
	sudo parted -s $diskToParted name 2 '2-Partition'

	#fsck -fy
	echo -e "\n"
	sudo fsck -f $firstPartition
	echo ""
	sudo fsck -f $secondPartition
	echo -e "\n"

	case $FileSystemType in
		ext2 )
			sudo mkfs.ext2 -F $firstPartition
			sudo mkfs.ext2 -F $secondPartition
			;;
        ext3 )
			sudo mkfs.ext3 -F $firstPartition
			sudo mkfs.ext3 -F $secondPartition
        	;;
        ext4 )
			sudo mkfs.ext4 -F $firstPartition
			sudo mkfs.ext4 -F $secondPartition
			;;
        * )
			echo -e "\n==> ERROR: Something happened in create"
			#exit 1
    esac

    sudo parted -s $diskToParted print

	echo -e "\t==> Partition function finished!"
}

function display(){
	sudo parted -l
}

function mountFunction(){
	if [ ! -d /mnt ]; then
		sudo mkdir -p /mnt
		sudo mkdir -p /mnt/dev
	fi

	if [ ! -d /mnt$partitionToMount ]; then
		sudo rm -rf /mnt$partitionToMount
		sudo mkdir -p /mnt$partitionToMount
	fi

	sudo mount $partitionToMount /mnt$partitionToMount
	echo -e "\t==> Mount function finished!"
}

function umountFunction(){
	sudo umount /mnt$partitionToUmount
	echo -e "\t==> Umount function finished!"
}


function help(){
	echo -e "\n#############################################"
	echo -e "USE './file-system.sh' with the following:"
	echo -e "\t-convert /partitionToConvert (e.g. '/dev/sdb1') \n\t\tConverting to ext4 on the selected partition\n"
	echo -e "\t-create /diskToParted (e.g. '/dev/sda') \n\t\tCreate a partition of the selected disk\n"
	echo -e "\t-display \n\t\tDisplay all useful disks on the system\n"
	echo -e "\t-mount /partitionToMount (e.g. '/dev/sdb1') \n\t\tMounting the selected partition\n"
	echo -e "\t-umount /partitionToUmount (e.g. '/dev/sdb1') \n\t\tUmounting the selected partition\n"
	echo -e "\t-help \n\t\tDisplay this help"
	echo -e "#############################################"
}


########################################
#
#	MAIN
#
########################################

if [ $# -gt 0 ]; then

	#echo -e $#
	case $1 in
		-convert )
			echo -e "\nConverting the file system to ext4 on the selected partition...!!\n"
				if [ $2 != "" ]; then
					partitionToConvert=$2
					convert
				else
					echo -e "\n\t==> ERROR: You have to select a partition"
					help
				fi
			echo -e "\n"
			;;
        -create | -c )
			echo -e "\nCreate a partition...!!\n"
				if [ $2 != "" ]; then
					diskToParted=$2

					FileSystemType=""
					read -p $'\t'"Select File System Type (ext2, ext3, ext4): " FileSystemType
					while [[ $FileSystemType != "ext2" && $FileSystemType != "ext3" && $FileSystemType != "ext4" ]]; do
						echo -en "\t\tWrong FS-TYPE\n"
						read -p $'\t'"Select File System Type (ext2, ext3, ext4): " FileSystemType
					done

					IFS='/' # hyphen (-) is set as delimiter
						read -ra ADDR <<< "$diskToParted" # str is read into an array as tokens separated by IFS
						DISK="${ADDR[2]}"
					IFS=' ' # reset to default value after usage

					create
				else
					echo -e "\n\t==> ERROR: You have to select a disk"
					help
				fi
			echo -e "\n"
        	;;
        -display | -d )
			echo -e "\nDisplay all useful disks on the system!!\n"
				display
			echo -e "\n"
			;;	
        -mount | -m )
        	if [ $2 != "" ]; then
				partitionToMount=$2
				mountFunction
			else
				echo -e "\n\t==> ERROR: You have to select a partition"
				help
			fi
        	;;
		-umount | -u )
			if [ $2 != "" ]; then
				partitionToUmount=$2
				umountFunction
			else
				echo -e "\n\t==> ERROR: You have to select a partition"
				help
			fi
			;;
        -help | -h )
			help
			;;
        * )
			help
			#exit 1
    esac
else
    help
fi




#sudo fdisk -l /dev/sd*
#lsblk /dev/sd*



#DISK1="$DISK$first" # e.g. sdb + 1 = sdb1
#DISK2="$DISK$second" # e.g. sdb + 2 = sdb2


#if [ ! -d /mnt ]; then
#	sudo mkdir /mnt
#fi

#if [ ! -d /mnt/$DISK ]; then
#	sudo rm -rf /mnt/$DISK1
#	sudo rm -rf /mnt/$DISK2

#	sudo mkdir /mnt/$DISK1
#	sudo mkdir /mnt/$DISK2
#fi

#sudo mount $firstPartition /mnt/$DISK1
#sudo mount $secondPartition /mnt/$DISK2


