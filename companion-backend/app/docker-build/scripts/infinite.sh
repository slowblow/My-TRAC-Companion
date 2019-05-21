#!/bin/bash
#!/bin/bash

echo "start infinite loop $(date)"
while [`ping localhost`]; do
    echo "infinite"
done
echo "finish infinite loop $(date)"
