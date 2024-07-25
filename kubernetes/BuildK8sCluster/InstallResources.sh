## Set up resources as ubuntu user

## Allow docker access for ubuntu user
sudo usermod -aG docker $USER

## Give kubernetes access for ubuntu user
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config

## Pull and install the calico pod CIDR
curl https://raw.githubusercontent.com/projectcalico/calico/v3.28.0/manifests/calico.yaml -O
kubectl apply -f calico.yaml

## Install k9s dashboard
curl -sS https://webi.sh/k9s | sh

